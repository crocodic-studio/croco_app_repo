package com.crocodic.crocodicrepo.image

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.support.v4.app.Fragment
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import com.livinglifetechway.quickpermissions_kotlin.util.QuickPermissionsOptions
import com.livinglifetechway.quickpermissions_kotlin.util.QuickPermissionsRequest
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import java.io.File
import java.io.FileOutputStream


class ImageUtil private constructor() {

    private var context: Any? = null


    private fun setContext(context: Any): ImageUtil {
        this@ImageUtil.context = context
        return this
    }

    fun setPickerType(type: Int): ImageUtil {
        TYPE_SELECTED = type
        return this
    }

    fun setKeyOfFile(key: Int): ImageUtil {
        KEY_OF_FILE = key
        return this
    }

    fun show(): ImageUtil {
        methodRequiresPermissions(context, permissionOptions(context))
        return this
    }

    private fun methodRequiresPermissions(activity: Any?, quickPermissionsOptions: QuickPermissionsOptions) {
        if (activity is Activity) {
            activity.runWithPermissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE, options = quickPermissionsOptions
            ) {
                when (TYPE_SELECTED) {
                    TYPE_ALL -> {
                        EasyImage.openChooserWithGallery(activity, "Choose Type", 1)
                    }
                    TYPE_CAMERA -> {
                        EasyImage.openCameraForImage(activity, 1)
                    }
                    TYPE_GALLERY -> {
                        EasyImage.openGallery(activity, 1)
                    }
                }
            }
        } else {
            if (activity is Fragment) {
                activity.runWithPermissions(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE, options = quickPermissionsOptions
                ) {
                    when (TYPE_SELECTED) {
                        TYPE_ALL -> {
                            EasyImage.openChooserWithGallery(activity, "Choose Type", 1)
                        }
                        TYPE_CAMERA -> {
                            EasyImage.openCameraForImage(activity, 1)
                        }
                        TYPE_GALLERY -> {
                            EasyImage.openGallery(activity, 1)
                        }
                    }
                }
            }
        }
    }


    private fun permissionOptions(activity: Any?): QuickPermissionsOptions {
        activity as Activity
        val options = QuickPermissionsOptions()
        options.handleRationale = true
        options.rationaleMessage = "Custom rational message"
        options.rationaleMethod = { rationaleCallback(activity, it) }
        options.handlePermanentlyDenied = true
        options.permanentlyDeniedMessage = "Custom permanently denied message"
        options.permanentDeniedMethod = { permissionsPermanentlyDenied(activity, it) }
        options.permissionsDeniedMethod = { whenPermAreDenied(activity, it) }
        return options
    }


    private fun rationaleCallback(activity: Activity, req: QuickPermissionsRequest) {
        AlertDialog.Builder(activity)
            .setTitle("Permissions Denied")
            .setMessage("This is the custom rationale dialog. Please allow us to proceed " + "asking for permissions again, or cancel to end the permission flow.")
            .setPositiveButton("Go Ahead") { dialog, which -> req.proceed() }
            .setNegativeButton("cancel") { dialog, which -> req.cancel() }
            .setCancelable(false)
            .show()
    }

    private fun permissionsPermanentlyDenied(activity: Activity, req: QuickPermissionsRequest) {
        AlertDialog.Builder(activity)
            .setTitle("Permissions Denied")
            .setMessage(
                "This is the custom permissions permanently denied dialog. " +
                        "Please open app settings to open app settings for allowing permissions, " +
                        "or cancel to end the permission flow."
            )
            .setPositiveButton("App Settings") { dialog, which -> req.openAppSettings() }
            .setNegativeButton("Cancel") { dialog, which -> req.cancel() }
            .setCancelable(false)
            .show()
    }

    private fun whenPermAreDenied(activity: Activity, req: QuickPermissionsRequest) {
        AlertDialog.Builder(activity)
            .setTitle("Permissions Denied")
            .setMessage("This is the custom permissions denied dialog. \n${req.deniedPermissions.size}/${req.permissions.size} permissions denied")
            .setPositiveButton("OKAY") { _, _ -> }
            .setCancelable(false)
            .show()
    }


    companion object {
        var TYPE_ALL = 0
        var TYPE_CAMERA = 1
        var TYPE_GALLERY = 2
        var TYPE_SELECTED = 0
        var KEY_OF_FILE = 0

        fun with(activity: Activity?): ImageUtil {
            if (activity == null) {
                throw IllegalArgumentException("Activity cannot be null!")
            }
            val imageUtil = ImageUtil()
            imageUtil.setContext(activity)

            return imageUtil
        }

        fun with(fragment: Fragment?): ImageUtil {
            if (fragment == null) {
                throw IllegalArgumentException("Fragment cannot be null!")
            }
            val imageUtil = ImageUtil()
            imageUtil.setContext(fragment)

            return imageUtil
        }

        fun listOfFiles(activity: Activity, requestCode: Int, resultCode: Int, data: Intent?): List<File> {
            val mImageFiles = ArrayList<File>()
            EasyImage.handleActivityResult(requestCode, resultCode, data, activity, object : DefaultCallback() {
                override fun onImagePickerError(e: Exception?, source: EasyImage.ImageSource?, type: Int) {
                    if (source == EasyImage.ImageSource.CAMERA_IMAGE) {
                        val photoFile = EasyImage.lastlyTakenButCanceledPhoto(activity)
                        photoFile?.delete()
                    }
                }

                override fun onImagesPicked(imagesFiles: List<File>, source: EasyImage.ImageSource, type: Int) {
                    KEY_OF_FILE = type
                    imagesFiles.forEach {
                        applyRotationIfNeeded(it)
                    }
                    mImageFiles.addAll(imagesFiles)
                }
            })
            return mImageFiles
        }

        private fun applyRotationIfNeeded(imageFile: File) {
            val exif = ExifInterface(imageFile.absolutePath)
            val exifRotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED)
            val rotation = when (exifRotation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> 90
                ExifInterface.ORIENTATION_ROTATE_180 -> 180
                ExifInterface.ORIENTATION_ROTATE_270 -> 270
                else -> 0
            }
            if (rotation == 0) return
            val bitmap = BitmapFactory.decodeFile(imageFile.absolutePath)
            val matrix = Matrix().apply { postRotate(rotation.toFloat()) }
            val rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
            bitmap.recycle()

            lateinit var out: FileOutputStream
            try {
                out = FileOutputStream(imageFile)
                rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            } catch (e: Exception) {

            } finally {
                rotatedBitmap.recycle()
                out.close()
            }
        }

    }
}
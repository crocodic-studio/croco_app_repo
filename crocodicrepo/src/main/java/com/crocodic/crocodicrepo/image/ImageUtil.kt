package com.crocodic.crocodicrepo.image

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import com.livinglifetechway.quickpermissions_kotlin.util.QuickPermissionsOptions
import com.livinglifetechway.quickpermissions_kotlin.util.QuickPermissionsRequest
import pl.aprilapps.easyphotopicker.EasyImage

class ImageUtil : AppCompatActivity() {

    companion object {

        fun showAllPhotoMethod(activity: Activity, chooserTitle: String, type: Int) {
            methodRequiresPermissions(activity, permissionOptions(activity))
        }


        private fun permissionOptions(activity: Activity): QuickPermissionsOptions {
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


        private fun methodRequiresPermissions(activity: Activity, quickPermissionsOptions: QuickPermissionsOptions) =
            activity.runWithPermissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE, options = quickPermissionsOptions
            ) {
                EasyImage.openChooserWithGallery(activity, "Pilih Tipe", 1)
            }

        private fun rationaleCallback(activity: Activity, req: QuickPermissionsRequest) {
            // this will be called when permission is denied once or more time. Handle it your way
            AlertDialog.Builder(activity)
                .setTitle("Permissions Denied")
                .setMessage("This is the custom rationale dialog. Please allow us to proceed " + "asking for permissions again, or cancel to end the permission flow.")
                .setPositiveButton("Go Ahead") { dialog, which -> req.proceed() }
                .setNegativeButton("cancel") { dialog, which -> req.cancel() }
                .setCancelable(false)
                .show()
        }

        private fun permissionsPermanentlyDenied(activity: Activity, req: QuickPermissionsRequest) {
            // this will be called when some/all permissions required by the method are permanently
            // denied. Handle it your way.
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
            // handle something when permissions are not granted and the request method cannot be called
            AlertDialog.Builder(activity)
                .setTitle("Permissions Denied")
                .setMessage("This is the custom permissions denied dialog. \n${req.deniedPermissions.size}/${req.permissions.size} permissions denied")
                .setPositiveButton("OKAY") { _, _ -> }
                .setCancelable(false)
                .show()


        }
    }
}
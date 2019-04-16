package com.crocodic.crocodicrepo.image

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.theartofdev.edmodo.cropper.CropImage
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import java.io.File


infix fun Activity.openPicker(PICKER_TYPE : Int){
    val i = Intent(this, ImagePickerCrop::class.java)
    i.putExtra(ImagePickerCrop.KEY_PICKER, ImagePickerCrop.PICKER_GALLERY)
    startActivityForResult(i, 1)
}

class ImagePickerCrop : AppCompatActivity() {


    companion object {
        val KEY_PICKER = "fromIntent"
        val KEY_PICKER_PATH = "path"

        var fromIntent = 0

        val PICKER_CAMERA = 1
        val PICKER_GALLERY = 2
        val RESULT_PICKER_OK = 99

        fun openPicker(myActivity: Activity, PICKER_TYPE: Int){
            myActivity openPicker PICKER_TYPE
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fromIntent = intent.getIntExtra(KEY_PICKER, 0)

        if (fromIntent == PICKER_CAMERA){
            EasyImage.openCameraForImage(this,0)
        }else{
            EasyImage.openGallery(this,0)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (resultCode == 0){
            finish()
            return
        }

        EasyImage.handleActivityResult(requestCode, resultCode, data, this,
            object : DefaultCallback() {
                override fun onImagePickerError(e: Exception?, source: EasyImage.ImageSource?, type: Int) {
                    finish()
                }

                override fun onImagesPicked(imagesFiles: List<File>, source: EasyImage.ImageSource, type: Int) {
                    CropImage.activity(Uri.fromFile(imagesFiles[0]))
                        .start(this@ImagePickerCrop)
                }

                override fun onCanceled(source: EasyImage.ImageSource, type: Int) {
                    // Cancel handling, you might wanna remove taken photo if it was canceled
                    if (source == EasyImage.ImageSource.CAMERA_IMAGE) {
                        val photoFile = EasyImage.lastlyTakenButCanceledPhoto(this@ImagePickerCrop)
                        photoFile?.delete()
                        finish()
                    }
                }
            })


        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                val resultUri = result.uri
                setResult(resultUri.path)

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
                finish()
            }
        }
    }


    fun setResult(path: String){
        val resultIntent = intent
        resultIntent.putExtra(KEY_PICKER_PATH, path)
        setResult(RESULT_PICKER_OK, resultIntent)
        finish()
    }
}

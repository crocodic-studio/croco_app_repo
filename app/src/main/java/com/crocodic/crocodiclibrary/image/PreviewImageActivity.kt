package com.crocodic.crocodiclibrary.image

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.crocodic.crocodiclibrary.R
import com.crocodic.crocodicrepo.image.ImagePickerCrop
import com.crocodic.crocodicrepo.image.PreviewImage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_preview_image.*
import java.io.File

class PreviewImageActivity : AppCompatActivity(), View.OnClickListener {

    var urlImage = "https://pbs.twimg.com/profile_images/905640995924074496/xZCFjRE-.jpg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview_image)

        Picasso.get().load(urlImage).into(ivImage)

        ivImage.setOnClickListener(this)
        btnHaveImage.setOnClickListener(this)
        btnNoImage.setOnClickListener(this)
        btnPickCamera.setOnClickListener(this)
        btnPickGallery.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.ivImage -> {
                PreviewImage().load(urlImage, ivImage, this)
            }
            R.id.btnHaveImage -> {
                PreviewImage().load(urlImage, ivImage, this)
            }
            R.id.btnNoImage -> {
                PreviewImage().load("", ivImage, this)
            }

            R.id.btnPickCamera -> {
                ImagePickerCrop.openPicker(this@PreviewImageActivity, ImagePickerCrop.PICKER_CAMERA)
            }

            R.id.btnPickGallery -> {
                ImagePickerCrop.openPicker(this@PreviewImageActivity, ImagePickerCrop.PICKER_GALLERY)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == ImagePickerCrop.RESULT_PICKER_OK){
            val path = data?.getStringExtra(ImagePickerCrop.KEY_PICKER_PATH)
        }
    }
}

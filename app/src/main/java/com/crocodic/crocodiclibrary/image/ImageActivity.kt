package com.crocodic.crocodiclibrary.image

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.crocodic.crocodiclibrary.R
import com.crocodic.crocodicrepo.image.ImageUtil
import kotlinx.android.synthetic.main.activity_image.*
import java.io.File


class ImageActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
        btnUpload.setOnClickListener {
            ImageUtil
                .with(this)
                .setKeyOfFile(1)
                .setPickerType(ImageUtil.TYPE_ALL)
                .show()

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (ImageUtil.KEY_OF_FILE == 1) {
            val items = ImageUtil.listOfFiles(this, requestCode, resultCode, data)
            ivItem.setImageBitmap(showImageBitmap(items[0]))
        }
    }

    private fun showImageBitmap(file: File): Bitmap? {
        return BitmapFactory.decodeFile(file.path)
    }

}

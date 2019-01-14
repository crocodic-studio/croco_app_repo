package com.crocodic.crocodiclibrary.image

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.crocodic.crocodiclibrary.R
import com.crocodic.crocodicrepo.image.ImageUtil
import kotlinx.android.synthetic.main.activity_image.*


class ImageActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)


        btnUpload.setOnClickListener {
            ImageUtil.showAllPhotoMethod(this, "Pilih image", 0)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

    }
}

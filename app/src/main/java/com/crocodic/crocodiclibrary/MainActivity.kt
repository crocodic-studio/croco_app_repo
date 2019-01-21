package com.crocodic.crocodiclibrary

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.crocodic.crocodiclibrary.date.DateActivity
import com.crocodic.crocodiclibrary.image.ImageActivity
import com.crocodic.crocodiclibrary.notif.NotifInAppActivity
import com.crocodic.crocodicrepo.log.LogUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btnDate.setOnClickListener(this)
        btnNotif.setOnClickListener(this)
        btnImage.setOnClickListener(this)

        LogUtil.e("test")

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnDate -> {
                startActivity(Intent(this, DateActivity::class.java))
            }
            R.id.btnNotif -> {
                startActivity(Intent(this, NotifInAppActivity::class.java))
            }
            R.id.btnImage -> {
                startActivity(Intent(this, ImageActivity::class.java))
            }
        }
    }

}

package com.crocodic.crocodiclibrary.notif

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.crocodic.crocodiclibrary.MainActivity
import com.crocodic.crocodiclibrary.R
import com.crocodic.crocodicrepo.notif.Alerter
import kotlinx.android.synthetic.main.activity_notif_in_app.*

class NotifInAppActivity : AppCompatActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notif_in_app)

        btnNotifInApp.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnNotifInApp -> {
                showNotificationInApp()
            }
        }
    }


    private fun showNotificationInApp() {
        Alerter.create(this)
            .setTitle("Notification")
            .setText("New notification")
            .setDuration(5000)
            .enableSwipeToDismiss()
            .setOnClickListener(View.OnClickListener { view ->
                val notif = Intent(this, MainActivity::class.java)
                startActivity(notif)
            })
            .show()
    }


}

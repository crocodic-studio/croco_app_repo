package com.crocodic.crocodiclibrary.adapter

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.crocodic.crocodiclibrary.R
import com.crocodic.crocodiclibrary.adapter.listener.ClickListenerAdapter
import com.crocodic.crocodiclibrary.extention.onClick
import com.crocodic.crocodiclibrary.extention.showToast
import com.crocodic.crocodiclibrary.extention.start
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_list.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val strJson = intent.getStringExtra("data")

        tvData.text = strJson

    }
}

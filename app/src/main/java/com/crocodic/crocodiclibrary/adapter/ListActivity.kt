package com.crocodic.crocodiclibrary.adapter

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.crocodic.crocodiclibrary.R
import com.crocodic.crocodiclibrary.adapter.listener.ClickListenerAdapter
import com.crocodic.crocodiclibrary.extention.onClick
import com.crocodic.crocodiclibrary.extention.showToast
import com.crocodic.crocodiclibrary.extention.start
import kotlinx.android.synthetic.main.activity_list.*

class ListActivity : AppCompatActivity(), ClickListenerAdapter {

    private var listMember : ArrayList<Member> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        listMember.add(Member(1,"Lorem Ipsum","Semarang","https://firebasestorage.googleapis.com/v0/b/modul-74a9a.appspot.com/o/img_thumbnail.png?alt=media&token=16f41b4f-22cf-4d8b-890b-98fc9c6f32e4"))
        listMember.add(Member(2,"Asit Amed","Semarang","https://firebasestorage.googleapis.com/v0/b/modul-74a9a.appspot.com/o/img_thumbnail.png?alt=media&token=16f41b4f-22cf-4d8b-890b-98fc9c6f32e4"))

        val memberAdapter = MemberAdapter(listMember, this)

        btnClick.onClick {
            start<ListActivity>()
        }

        with(rvList) {
            layoutManager = LinearLayoutManager(this@ListActivity)
            setHasFixedSize(true)
            adapter = memberAdapter
        }
    }

    override fun onEventClicked(item: Member) {
        showToast("ItemClick : "+ item.id.toString())
    }
}

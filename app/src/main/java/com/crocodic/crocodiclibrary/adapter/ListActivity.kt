package com.crocodic.crocodiclibrary.adapter

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.crocodic.crocodiclibrary.R
import com.crocodic.crocodiclibrary.extention.start
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.item_member.view.*

class ListActivity : AppCompatActivity() {

    private var listMember : ArrayList<Member> = ArrayList()
    lateinit var recyclerAdapter : RecyclerAdapter<Member>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        listMember.add(Member(1,"Lorem Ipsum","Semarang","https://firebasestorage.googleapis.com/v0/b/modul-74a9a.appspot.com/o/img_thumbnail.png?alt=media&token=16f41b4f-22cf-4d8b-890b-98fc9c6f32e4"))
        listMember.add(Member(2,"Asit Amed","Semarang","https://firebasestorage.googleapis.com/v0/b/modul-74a9a.appspot.com/o/img_thumbnail.png?alt=media&token=16f41b4f-22cf-4d8b-890b-98fc9c6f32e4"))

        // TODO Alternative Adapter 1

        rvList.apply {
            val memberAdapter = MemberAdapter(listMember){
                // TODO Click Item, this = Item, it = positionClick
                start<DetailActivity>("data",this.name)
            }
            layoutManager = LinearLayoutManager(this@ListActivity)
            setHasFixedSize(true)
            adapter = memberAdapter
        }


        // TODO Alternative Adapter 2

        rvList.apply {
            recyclerAdapter = RecyclerAdapter(listMember,R.layout.item_member,{
                tvName.text = it.name
                tvLocation.text = it.address
            },{
                // TODO Click Item
                start<DetailActivity>("data",this.name)
            })
            layoutManager = LinearLayoutManager(this@ListActivity)
            setHasFixedSize(true)
            adapter = recyclerAdapter
        }
    }
}

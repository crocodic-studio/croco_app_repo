package com.crocodic.crocodiclibrary.adapter

import android.view.View
import com.crocodic.crocodiclibrary.R
import kotlinx.android.synthetic.main.item_member.view.*

class MemberAdapter(
    memberList: ArrayList<Member>
) : BaseAdapter<Member>(memberList, R.layout.item_member) {

    private var itemClick: Member.(Int) -> Unit = {}

    constructor(items: ArrayList<Member>,
                itemClick: Member.(Int) -> Unit = {}) : this(items) {
        this.itemClick = itemClick
    }

    override fun View.bind(item: Member) {
        tvName.text = item.name
        tvLocation.text = item.address
    }

    override fun onItemClick(itemView: View, position: Int) {
        itemClick(itemList[position],position)
    }
}
package com.crocodic.crocodiclibrary.adapter

import android.view.View
import com.crocodic.crocodiclibrary.adapter.listener.ClickListenerAdapter
import com.crocodic.crocodiclibrary.extention.L
import kotlinx.android.synthetic.main.item_member.view.*

class MemberAdapter constructor(
    memberList: ArrayList<Member>,
    private val clickListener: ClickListenerAdapter
) : BaseAdapter<Member>(memberList, L.item_member) {

    override fun View.bind(item: Member) {
        tvName.text = item.name
        tvLocation.text = item.address
    }

    override fun onItemClick(itemView: View, position: Int) {
        clickListener.onEventClicked(itemList[position])
    }
}
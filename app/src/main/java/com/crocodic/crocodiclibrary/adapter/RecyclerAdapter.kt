package com.crocodic.crocodiclibrary.adapter

import android.view.View


class RecyclerAdapter<ITEM>(items: List<ITEM>,
                            layoutResId: Int,
                            private val bindHolder: View.(ITEM) -> Unit)
    : BaseAdapter<ITEM>(items as ArrayList<ITEM>, layoutResId) {

    private var itemClick: ITEM.(Int) -> Unit = {}

    constructor(items: List<ITEM>,
                layoutResId: Int,
                bindHolder: View.(ITEM) -> Unit,
                itemClick: ITEM.(Int) -> Unit = {}) : this(items,layoutResId, bindHolder) {
        this.itemClick = itemClick
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.itemView.bindHolder(itemList[position])
    }

    override fun onItemClick(itemView: View, position: Int) {
        itemList[position].itemClick(position)
    }
}
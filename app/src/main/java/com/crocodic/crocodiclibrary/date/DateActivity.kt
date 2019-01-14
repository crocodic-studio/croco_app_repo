package com.crocodic.crocodiclibrary.date

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.crocodic.crocodiclibrary.R
import com.crocodic.crocodicrepo.date.DateUtil
import com.crocodic.crocodicrepo.date.TimeAgoUtil
import kotlinx.android.synthetic.main.activity_date.*

class DateActivity : AppCompatActivity() {

    private val timeAgo = TimeAgoUtil()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date)

        timeAgo.locale(this)


        tvDate.text = DateUtil.getCurrDate(DateUtil.FORMAT_THREE)
        tvTimeAgo.text = timeAgo.getTimeAgo(
            DateUtil.stringtoDate("2019-01-02 23:04:11", DateUtil.FORMAT_ONE)
        )


    }
}

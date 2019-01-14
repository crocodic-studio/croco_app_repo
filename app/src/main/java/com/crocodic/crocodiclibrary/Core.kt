package com.crocodic.crocodiclibrary

import android.app.Application
import com.crocodic.crocodicrepo.log.LogUtil


class Core : Application() {

    override fun onCreate() {
        super.onCreate()
        setUpLogger(true)
    }


    fun setUpLogger(debuggable: Boolean) {
        LogUtil.setLogger(object : LogUtil.ConCreateLog() {
            override fun isDebug(): Boolean {
                return debuggable
            }

            override fun getTag(): String {
                return "MyTag"
            }

        })
    }


}
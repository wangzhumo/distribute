package com.wangzhumo.distribute

import android.app.Application

/**
 * If you have any questions, you can contact by email {wangzhumoo@gmail.com}
 *
 * @author 王诛魔 2021/12/24 2:07
 *
 * App
 */
class APP :Application() {

    init {
        application = this
    }

    override fun onCreate() {
        super.onCreate()
    }


    companion object{
        lateinit var application:APP
    }

}
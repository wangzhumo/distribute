package com.wangzhumo.distribute.datasource

data class Events(val action:Int)


class HomePageEvent(val action:Int, val info:AppRemoteWrapper)

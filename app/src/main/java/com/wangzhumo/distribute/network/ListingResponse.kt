package com.wangzhumo.distribute.network

/**
 * If you have any questions, you can contact by email {wangzhumoo@gmail.com}
 *
 * @author 王诛魔 2021/12/24 10:24
 *
 * 列表信息
 */
class ListingResponse<T>(val last_id:Long = 0, private val more:Boolean = false, private val data:List<T>) {

    // 是否成功
    fun isSuccess():Boolean{
        return !data.isNullOrEmpty()
    }

    fun getList() : List<T>{
        return data
    }

    // 是否还有更多可以加载
    fun hasMore():Boolean{
        return more
    }
}
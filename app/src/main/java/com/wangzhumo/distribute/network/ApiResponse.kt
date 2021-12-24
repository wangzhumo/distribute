package com.wangzhumo.distribute.network

/**
 * If you have any questions, you can contact by email {wangzhumoo@gmail.com}
 *
 * @author 王诛魔 2021/12/24 10:40
 *
 * 普通返回
 */
class ApiResponse<T>(val code:Int = -1,val message:String = "ok",val data:T)  {

    // 是否成功
    fun isSuccess():Boolean{
        return code == 0
    }


}
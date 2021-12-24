package com.wangzhumo.distribute.datasource

import androidx.annotation.AnyThread
import androidx.paging.PagingConfig

const val PAGING_REMOTE_PAGE_SIZE = 10

//  单独配置  全局分页配置
val globalPagingConfig: PagingConfig
    @AnyThread get() = PagingConfig(
        pageSize = PAGING_REMOTE_PAGE_SIZE,
        enablePlaceholders = false             // 定义[PagingData]是否可以显示“null”占位符
    )



data class PageParams(val lastId:Long,val type:String,val id:Int){
    fun next(lastId: Long):PageParams{
        return PageParams(
            lastId = lastId,
            type = type,
            id = id
        )
    }
}
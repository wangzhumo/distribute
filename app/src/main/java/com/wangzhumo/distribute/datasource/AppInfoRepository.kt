package com.wangzhumo.distribute.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.wangzhumo.distribute.network.ApiException
import com.wangzhumo.distribute.network.AppProjectApi


class AppInfoRepository(
    private val service: AppProjectApi,
    private val appId: Int
) : PagingSource<PageParams, VersionInfo>() {

    override fun getRefreshKey(state: PagingState<PageParams, VersionInfo>): PageParams? {
        return PageParams(0, "all", appId)
    }

    override suspend fun load(params: LoadParams<PageParams>): LoadResult<PageParams, VersionInfo> {
        return try {
            params.key?.let {
                val pageList =
                    service.requestVersionList(it.lastId, params.loadSize, it.type, it.id)

                LoadResult.Page(
                    data = pageList.getList(),
                    prevKey = if (it.lastId == 0L) {
                        null
                    } else {
                        it
                    },
                    nextKey = if (pageList.hasMore()) {
                        it.next(pageList.last_id)
                    } else {
                        null
                    }
                )
            } ?: LoadResult.Error(ApiException(""))
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
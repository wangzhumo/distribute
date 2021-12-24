package com.wangzhumo.distribute.pages.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import com.wangzhumo.distribute.APP
import com.wangzhumo.distribute.datasource.AppInfoRepository
import com.wangzhumo.distribute.datasource.AppProject
import com.wangzhumo.distribute.datasource.LocalAppProject
import com.wangzhumo.distribute.datasource.globalPagingConfig
import com.wangzhumo.distribute.network.AppProjectApi
import com.wangzhumo.distribute.utils.ApkAnalyseUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class HomePageViewModel(val appProject: AppProject,private val api: AppProjectApi) : ViewModel() {


    /**
     * 本地收集的数据
     */
    val localProjectList = MutableStateFlow<LocalAppProject?>(null)

    init {
        viewModelScope.launch {
            // 查询本地的App数据
            localProjectList.emit(ApkAnalyseUtils.getAppInfo(APP.application, appProject.app_id))
        }
    }

    /**
     * 获取Version的大列表
     */
    val remoteVersionData = Pager(
        config = globalPagingConfig,
        pagingSourceFactory = {
            AppInfoRepository(api,appProject.id)
        }
    ).flow.cachedIn(viewModelScope)

    class HomePageViewModelFactory(private val api: AppProjectApi, private val app: AppProject) :
        ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomePageViewModel::class.java)) {
                return HomePageViewModel(app,api) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
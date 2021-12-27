package com.wangzhumo.distribute.pages.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import com.wangzhumo.distribute.APP
import com.wangzhumo.distribute.datasource.*
import com.wangzhumo.distribute.network.AppProjectApi
import com.wangzhumo.distribute.utils.ApkAnalyseUtils
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch

/**
 * If you have any questions, you can contact by email {wangzhumoo@gmail.com}
 *
 * @author 王诛魔 2021/12/27 2:02
 *
 * 每一个Page自己的ViewModel
 */
class HomePageViewModel(val appProject: AppProject,private val api: AppProjectApi) : ViewModel() {


    /**
     * 转发
     */
    val event = MutableSharedFlow<HomePageEvent>(0)

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


    /**
     * 点击，跳转到详情页面
     */
    fun onClickItem(info: AppRemoteWrapper) {
       viewModelScope.launch {
           event.emit(HomePageEvent(EVENT_JUMP_DETAIL,info))
       }
    }

    /**
     * 开始下载这个对应的Apk，并且开始安装流程
     */
    fun onLongClickItem(info: AppRemoteWrapper) {
        viewModelScope.launch {
            event.emit(HomePageEvent(EVENT_DOWNLOAD_APK,info))
        }
    }

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

    companion object{
        const val EVENT_JUMP_DETAIL = 1
        const val EVENT_DOWNLOAD_APK = 2
    }
}
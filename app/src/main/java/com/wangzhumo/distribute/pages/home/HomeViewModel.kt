package com.wangzhumo.distribute.pages.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewModelScope
import com.wangzhumo.distribute.datasource.*
import com.wangzhumo.distribute.network.AppProjectApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * If you have any questions, you can contact by email {wangzhumoo@gmail.com}
 *
 * @author 王诛魔 2021/12/21 2:05
 *
 * Home页面数据
 */
class HomeViewModel(private val service: AppProjectApi) : ViewModel() {

    // 页面数据
    val projectList = listOf(
        AppProject(
            0,
            "http://distribute.wangzhumo.com/static/icons/370093b5-8cee-4342-9652-f290855befca.png",
            "音遇",
            "io.liuliu.music"
        ),
        AppProject(
            1,
            "http://distribute.wangzhumo.com/static/icons/370093b5-8cee-4342-9652-f290855befca.png",
            "哟密",
            "io.weixiang.umi"
        ),
    )

    /**
     * 转发
     */
    val eventBus = MutableSharedFlow<HomePageEvent>(0)


    /**
     * page的ViewModel
     */
    private var _listViewModel = mutableListOf<HomePageViewModel>()


    /**
     * 初始化projectList对应的ViewModel
     */
    fun initPageViewModel(storeOwner: ViewModelStoreOwner) {
        // 初始化几个ViewModel
        projectList.forEach {
            _listViewModel.add(
                ViewModelProvider(
                    storeOwner,
                    HomePageViewModel.HomePageViewModelFactory(service, it)
                )["ViewModel-${it.app_id}", HomePageViewModel::class.java]
            )
        }
        // 监听
        viewModelScope.launch {
            _listViewModel.forEach {
                it.event.collect { event ->
                    eventBus.emit(event)
                }
            }
        }
    }

    fun getPageViewModel(index: Int): HomePageViewModel {
        return _listViewModel[index]
    }


    class HomeViewModelFactory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                return HomeViewModel(AppProjectApi.create()) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }


}
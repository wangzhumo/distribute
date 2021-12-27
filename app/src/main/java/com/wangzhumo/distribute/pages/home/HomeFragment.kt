package com.wangzhumo.distribute.pages.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.wangzhumo.distribute.R
import com.wangzhumo.distribute.Screen
import com.wangzhumo.distribute.navigate
import com.wangzhumo.distribute.ui.theme.AppMainTheme
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * If you have any questions, you can contact by email {wangzhumoo@gmail.com}
 *
 * @author 王诛魔 2021/12/27 2:43
 *
 * 首页显示App的List
 */
class HomeFragment() : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private val viewModel: HomeViewModel by viewModels<HomeViewModel> {
        HomeViewModel.HomeViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 初始化ViewModel
        viewModel.initPageViewModel(this@HomeFragment)
        lifecycleScope.launch {
            // 监听数据
            viewModel.eventBus
                .collect {
                    when(it.action){
                        HomePageViewModel.EVENT_DOWNLOAD_APK -> {

                        }
                        HomePageViewModel.EVENT_JUMP_DETAIL -> {
                            val bundle = Bundle()
                            bundle.putSerializable("INFO",it.info)
                            navigate(Screen.DETAIL,Screen.HOME, bundle = bundle)
                        }
                    }
                }
        }
    }


    @ExperimentalPagerApi
    @ExperimentalFoundationApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // 返回View
        return ComposeView(requireContext()).apply {
            id = R.id.screen_search_view
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            setContent {
                AppMainTheme {
                    ViewPagerScreen()
                }
            }
        }
    }


    @ExperimentalFoundationApi
    @ExperimentalPagerApi
    @Composable
    fun ViewPagerScreen() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            // Remember a PagerState
            val pagerState = rememberPagerState()
            HorizontalPager(
                count = viewModel.projectList.size,
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                HomeProjectPage(page)
            }
        }
    }

}
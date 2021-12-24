package com.wangzhumo.distribute.pages.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.wangzhumo.distribute.R
import com.wangzhumo.distribute.ui.theme.AppMainTheme

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private val viewModel: HomeViewModel by viewModels<HomeViewModel> {
        HomeViewModel.HomeViewModelFactory()
    }

    @ExperimentalPagerApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // 初始化ViewModel
        viewModel.initPageViewModel(this)
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


    @ExperimentalPagerApi
    @Composable
    @Preview
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
package com.wangzhumo.distribute.pages.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.wangzhumo.distribute.R
import com.wangzhumo.distribute.components.AppInfoComponent
import com.wangzhumo.distribute.components.TextItemComponent
import com.wangzhumo.distribute.datasource.AppProjectWrapper
import com.wangzhumo.distribute.datasource.LocalAppProject

@Composable
fun HomeProjectPage(index: Int, modifier: Modifier = Modifier) {
    val viewModel: HomeViewModel = viewModel()
    RefreshLoadUse(viewModel.getPageViewModel(index))
}


@Composable
fun RefreshLoadUse(pageViewModel: HomePageViewModel) {
    // 数据
    val collectVersionPagingItems = pageViewModel.remoteVersionData.collectAsLazyPagingItems()
    val refreshState =
        rememberSwipeRefreshState(isRefreshing = true)

    SwipeRefresh(
        state = refreshState,
        onRefresh = {
            collectVersionPagingItems.refresh()
        },
        modifier = Modifier.statusBarsPadding()
    ) {
        // 刷新状态
        refreshState.isRefreshing = collectVersionPagingItems.loadState.refresh is LoadState.Loading

        Column {
            AppInfoHeader(pageViewModel)
            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                collectVersionPagingItems.apply {
                    when {
                        loadState.append is LoadState.Loading -> {
                            item {
                                LoadingItem()
                            }
                        }
                        loadState.append is LoadState.Error -> {
                            item {
                                ErrorItem {
                                    collectVersionPagingItems.retry()
                                }
                            }
                        }
                        loadState.refresh is LoadState.Error -> {

                            if (collectVersionPagingItems.itemCount <= 0) {
                                item {
                                    ErrorContent {
                                        collectVersionPagingItems.retry()
                                    }
                                }
                            } else {
                                item {
                                    ErrorItem() {
                                        collectVersionPagingItems.retry()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun ErrorItem(retry: () -> Unit) {
    Button(onClick = { retry() }, modifier = Modifier.padding(10.dp)) {
        Text(text = "重试")
    }
}

@Composable
fun ErrorContent(retry: () -> Unit) {
    Image(
        modifier = Modifier.padding(top = 80.dp),
        painter = painterResource(id = R.drawable.login_ic_yinyu_pic),
        contentDescription = null
    )
    Text(text = "请求出错啦")
    Button(onClick = { retry() }, modifier = Modifier.padding(10.dp)) {
        Text(text = "重试")
    }
}

@Composable
fun LoadingItem() {
    CircularProgressIndicator(modifier = Modifier.padding(10.dp))
}


@Composable
fun AppInfoHeader(viewModel: HomePageViewModel) {
    val collectAsState = viewModel.localProjectList.value
    val appProject = viewModel.appProject
    Column {
        AppInfoComponent(AppProjectWrapper(appProject, collectAsState))
        CurrentInstallVersion(collectAsState)
    }
}

@Composable
fun CurrentInstallVersion(collectAsState: LocalAppProject?) {
    if (collectAsState != null){
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier
            .fillMaxWidth()
            .size(30.dp)) {
            TextItemComponent(value = collectAsState.versionName, title = "版本名")
            Spacer(modifier = Modifier
                .size(1.dp, 9.dp)
                .background(Color.LightGray))
            TextItemComponent(value = collectAsState.versionCode.toString(), title = "版本号")
            Spacer(modifier = Modifier
                .size(1.dp, 9.dp)
                .background(Color.LightGray))
            TextItemComponent(value = collectAsState.androidCode, title = "系统版本")
        }
    }

}



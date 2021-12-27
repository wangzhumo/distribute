package com.wangzhumo.distribute.pages.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.wangzhumo.distribute.R
import com.wangzhumo.distribute.components.AppInfoComponent
import com.wangzhumo.distribute.components.TextItemComponent
import com.wangzhumo.distribute.datasource.AppProjectWrapper
import com.wangzhumo.distribute.datasource.AppRemoteWrapper
import com.wangzhumo.distribute.datasource.LocalAppProject
import com.wangzhumo.distribute.datasource.VersionInfo
import com.wangzhumo.distribute.ui.theme.BLACK_10
import com.wangzhumo.distribute.utils.TimeUtils

@ExperimentalFoundationApi
@Composable
fun HomeProjectPage(index: Int, modifier: Modifier = Modifier) {
    val viewModel: HomeViewModel = viewModel()
    RefreshLoadUse(viewModel.getPageViewModel(index))
}


@ExperimentalFoundationApi
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

                itemsIndexed(collectVersionPagingItems) { index, item ->
                    ListContentItem(item, index, pageViewModel)
                }

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

@ExperimentalFoundationApi
@Composable
fun ListContentItem(info: VersionInfo?, index: Int, pageViewModel: HomePageViewModel) {
    if (index == 0) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        ) {
            ListContentItemHeader()
            ListContentItemView(info = info, pageViewModel = pageViewModel)
        }
    } else {
        ListContentItemView(info = info, pageViewModel = pageViewModel)
    }
    Divider(modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp), color = BLACK_10)
}

@ExperimentalFoundationApi
@Composable
fun ListContentItemView(info: VersionInfo?, pageViewModel: HomePageViewModel) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(50.dp)
            .combinedClickable(
                onClick = {
                    info?.run {
                        pageViewModel.onClickItem(AppRemoteWrapper(this,pageViewModel.appProject))
                    }

                }, onLongClick = {
                    info?.run {
                        pageViewModel.onLongClickItem(AppRemoteWrapper(this,pageViewModel.appProject))
                    }
                })
    ) {
        Text(
            text = info?.code.toString(),
            modifier = Modifier.weight(0.25F, true),
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            fontSize = 11.sp
        )
        Text(
            text = info?.name ?: "",
            modifier = Modifier.weight(0.2F, true),
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Start,
            fontSize = 11.sp
        )
        Text(
            text = info?.downloads.toString(),
            modifier = Modifier.weight(0.2F, true),
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            fontSize = 11.sp
        )
        Text(
            text = TimeUtils.formatMilli(info?.timestamp ?: 0L, TimeUtils.FORMAT_YMDHM),
            modifier = Modifier.weight(0.35F, true),
            fontSize = 11.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Normal
        )
    }
}

@Composable
fun ListContentItemHeader() {
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = "Build",
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(0.25F, true),
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = "版本",
            textAlign = TextAlign.Start,
            modifier = Modifier.weight(0.2F, true),
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = "下载次数",
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(0.2F, true),
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = "更新时间",
            modifier = Modifier.weight(0.35F, true),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold
        )
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .height(Dp.Infinity),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
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
        Divider(modifier = Modifier.padding(top = 20.dp))
    }
}

@Composable
fun CurrentInstallVersion(collectAsState: LocalAppProject?) {
    if (collectAsState != null) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .size(30.dp)
        ) {
            TextItemComponent(value = collectAsState.versionName, title = "版本名")
            Spacer(
                modifier = Modifier
                    .size(1.dp, 9.dp)
                    .background(Color.LightGray)
            )
            TextItemComponent(value = collectAsState.versionCode.toString(), title = "版本号")
            Spacer(
                modifier = Modifier
                    .size(1.dp, 9.dp)
                    .background(Color.LightGray)
            )
            TextItemComponent(value = collectAsState.androidCode, title = "系统版本")
        }
    }
}



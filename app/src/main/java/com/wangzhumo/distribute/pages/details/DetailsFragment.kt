package com.wangzhumo.distribute.pages.details

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Backpack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.semantics.SemanticsProperties.Text
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType.Companion.Text
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.fragment.NavHostFragment
import coil.compose.rememberImagePainter
import com.google.accompanist.insets.statusBarsPadding
import com.wangzhumo.distribute.R
import com.wangzhumo.distribute.datasource.AppRemoteWrapper
import com.wangzhumo.distribute.datasource.VersionInfo
import com.wangzhumo.distribute.ui.theme.*
import com.wangzhumo.distribute.utils.TimeUtils

/**
 * If you have any questions, you can contact by email {wangzhumoo@gmail.com}
 *
 * @author 王诛魔 2021/12/27 2:44
 *
 * App的详情页面
 */
class DetailsFragment : Fragment() {

    private var appInfo: AppRemoteWrapper? = null
    private var appName: String = ""

    companion object {
        fun newInstance() = DetailsFragment()
    }

    private lateinit var viewModel: DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appInfo = arguments?.run {
            getSerializable("INFO") as AppRemoteWrapper
        }
        appName = arguments?.run {
            getString("NAME")
        } ?: ""
        if (appInfo == null) {
            NavHostFragment.findNavController(this@DetailsFragment)
                .navigateUp()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[DetailsViewModel::class.java]
        return ComposeView(requireContext()).apply {
            id = R.id.screen_details_view
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            setContent {
                AppMainTheme {
                    DetailsContentView()
                }
            }
        }
    }


    @Composable
    @Preview
    fun DetailsContentView() {
        Scaffold(
            topBar = {
                TopAppBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .height(48.dp),
                    elevation = 0.dp,
                    backgroundColor = Color.White,
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBackIos,
                        contentDescription = "返回",
                        tint = Color.Black,
                        modifier = Modifier
                            .clickable {
                                //点击了按钮
                                NavHostFragment
                                    .findNavController(this@DetailsFragment)
                                    .navigateUp()
                            }
                            .padding(start = 12.dp, end = 12.dp)
                            .fillMaxHeight()
                    )
                    Text(
                        text = "${appInfo?.project?.apk_name}-${appInfo?.version?.name}",
                        fontSize = 18.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Black
                    )
                }
            }
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(15.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 10.dp),
                    text = "应用信息",
                    textAlign = TextAlign.Start,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Divider(color = BLACK_10)
                Row(
                    modifier = Modifier.padding(vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = rememberImagePainter(appInfo?.project?.app_icon),
                        contentDescription = null,
                        modifier = Modifier.size(95.dp)
                    )
                    Column(
                        modifier = Modifier.padding(horizontal = 8.dp)
                    ) {
                        Text(
                            text = "应用名:${appInfo?.project?.apk_name}",
                            textAlign = TextAlign.Start,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            color = BLACK_60
                        )
                        Text(
                            text = "版本名:${appInfo?.version?.name}",
                            textAlign = TextAlign.Start,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            color = BLACK_60
                        )
                        Text(
                            text = "版本号:${appInfo?.version?.code}",
                            textAlign = TextAlign.Start,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            color = BLACK_60
                        )
                        Text(
                            text = "发布时间:${
                                TimeUtils.formatMilli(
                                    appInfo?.version?.timestamp ?: 0L,
                                    TimeUtils.FORMAT2_YMDHM
                                )
                            }",
                            textAlign = TextAlign.Start,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            color = BLACK_60
                        )
                        Text(
                            text = "安装次数:${appInfo?.version?.downloads}",
                            textAlign = TextAlign.Start,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            color = BLACK_60
                        )
                    }
                }
                Divider(color = BLACK_10)
                Text(
                    modifier = Modifier.padding(vertical = 10.dp),
                    text = "更新日志",
                    textAlign = TextAlign.Start,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    modifier = Modifier.padding(bottom = 10.dp),
                    text = "${appInfo?.version?.changelog}",
                    textAlign = TextAlign.Start,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
                Divider(color = BLACK_10)
                Text(
                    modifier = Modifier.padding(vertical = 10.dp),
                    text = "二维码",
                    textAlign = TextAlign.Start,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter) {
                    Image(
                        painter = rememberImagePainter(appInfo?.project?.app_icon),
                        contentDescription = null,
                        modifier = Modifier.size(200.dp)
                    )
                }

                Column(
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(bottom = 50.dp)
                        .fillMaxHeight(),

                    ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Color.Blue,
                                shape = RoundedCornerShape(corner = CornerSize(10.dp))
                            )
                            .clickable {

                            }
                            .height(50.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "下载并安装",
                            textAlign = TextAlign.Center,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = WHITE_100
                        )
                        LinearProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(),
                            color = WHITE_40,
                            progress = 0.8F
                        )
                    }
                }
            }
        }
    }

}
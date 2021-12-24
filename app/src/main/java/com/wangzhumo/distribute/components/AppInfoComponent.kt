package com.wangzhumo.distribute.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.wangzhumo.distribute.R
import com.wangzhumo.distribute.datasource.AppProjectWrapper
import com.wangzhumo.distribute.utils.TimeUtils

@Composable
fun AppInfoComponent(wrapper: AppProjectWrapper) {
    val bgColor = Color(0xFF00BFFF)
    val updateTime = wrapper.localProject?.lastUpdateTime
    var updateText = "------"
    updateTime?.let {
        updateText = when {
            updateTime == 0L -> updateText
            updateTime > 0L -> {
                TimeUtils.formatMilli(updateTime, TimeUtils.FORMAT_YMDHMS)
            }
            else -> updateText
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(
            18.dp,
            30.dp
        )
    ) {
        Image(
            painter = rememberImagePainter(wrapper.appProject.app_icon),
            contentDescription = null,
            modifier = Modifier.size(75.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .size(75.dp)
                .padding(start = 18.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = wrapper.appProject.apk_name,
                    color = Color.Black,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 2.dp)
                )

                Box(modifier  = Modifier
                    .background(
                        bgColor,
                        shape = RoundedCornerShape(2.dp)
                    )){
                    Text(
                        text = updateText,
                        color = Color.White,
                        fontSize = 10.sp,
                        modifier= Modifier.padding(horizontal = 5.dp)
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.app_ic_launcher),
                        contentDescription = null,
                        Modifier.size(14.dp)
                    )
                    Text(
                        text = wrapper.appProject.app_id,
                        color = Color.DarkGray,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(bottom = 2.dp, start = 4.dp)
                    )
                }
            }
        }
    }
}


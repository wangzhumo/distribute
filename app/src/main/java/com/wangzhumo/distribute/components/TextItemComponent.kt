package com.wangzhumo.distribute.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.wangzhumo.distribute.ui.theme.BLACK_60

@Composable
fun TextItemComponent(value: String, title: String) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = value, fontWeight = FontWeight.SemiBold, color = Color.Black, fontSize = 12.sp)
        Text(text = title, fontWeight = FontWeight.Normal, color = BLACK_60, fontSize = 10.sp)
    }

}
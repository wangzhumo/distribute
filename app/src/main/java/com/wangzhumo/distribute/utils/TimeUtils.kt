package com.wangzhumo.distribute.utils

import java.text.SimpleDateFormat
import java.util.*

object TimeUtils {

    // 格式：年－月－日 时：分：秒
    const val FORMAT_YMDHMS = "YYYY-MM-dd HH:mm:ss"

    // 格式：年－月－日
    const val FORMAT_YMD = "YYYY-MM-dd"

    // 格式：年－月－日 时:分
    const val FORMAT_YMDHM = "YYYY-MM-dd HH:mm"

    // 格式：年/月/日 时：分：秒
    const val FORMAT2_YMDHMS = "YYYY/MM/dd HH:mm:ss"

    // 格式：年/月/日
    const val FORMAT2_YMD = "YYYY/MM/dd"

    // 格式：年/月/日 时:分
    const val FORMAT2_YMDHM = "YYYY/MM/dd HH:mm"


    /**
     * 格式化时间戳
     * @param timestamp 时间戳（毫秒）
     */
    fun formatMilli(timestamp: Long,format:String):String {
        val data = Date(timestamp)
        return SimpleDateFormat(format,Locale.getDefault()).format(data)
    }

    /**
     * 格式化时间戳
     * @param timestamp 时间戳（毫秒）
     */
    fun formatSeconds(timestamp: Long,format:String):String {
        val data = Date(timestamp * 1000)
        return SimpleDateFormat(format,Locale.getDefault()).format(data)
    }
}
package com.wangzhumo.distribute.datasource

import androidx.compose.runtime.Immutable

/**
 * If you have any questions, you can contact by email {wangzhumoo@gmail.com}
 *
 * @author 王诛魔 2021/12/23 7:48
 *
 * 包装AppProject的操作
 */
data class AppProjectWrapper(
    val appProject: AppProject,
    val localProject: LocalAppProject?
)


/**
 * 本地的App消息
 */
data class LocalAppProject(
    var appName: String,
    var versionCode: Int,
    var versionName: String,
    var androidCode: String,
    var lastUpdateTime:Long = 0
)


/**
 * 一个项目的基础信息
 */
@Immutable
data class AppProject(
    val id: Int,
    val app_icon: String,
    val apk_name: String,
    val app_id: String
)

/**
 * 一个版本的信息
 */
data class VersionInfo(
    val id: Int,
    val name: String,
    val code: Int,
    val downloads: Int,
    val changelog: String,
    val apk_url: String,
    val qrcode_url: String,
    val apk_id: Int,
    val release_version:Boolean,
    val timestamp:Long
)
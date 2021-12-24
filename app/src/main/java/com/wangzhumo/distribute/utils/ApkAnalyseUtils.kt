package com.wangzhumo.distribute.utils

import android.content.Context
import com.wangzhumo.distribute.datasource.LocalAppProject

/**
 * If you have any questions, you can contact by email {wangzhumoo@gmail.com}
 *
 * @author 王诛魔 2021/12/24 1:47
 *
 * 获取指定Apk并进行分析
 */
object ApkAnalyseUtils {

    /**
     * 获取指定包名的App信息
     */
    fun getAppInfo(ctx: Context, appIds: Set<String>): MutableList<LocalAppProject> {
        val packageManager = ctx.packageManager
        var installedPackages = packageManager.getInstalledPackages(0)
        installedPackages = installedPackages.filter {
            appIds.contains(it?.applicationInfo?.packageName)
        }
        val appInfoList = mutableListOf<LocalAppProject>()
        installedPackages.forEach {
            it?.let {
                appInfoList.add(
                    LocalAppProject(
                        it.packageName,
                        it.versionCode,
                        it.versionName,
                        android.os.Build.VERSION.RELEASE,
                        it.lastUpdateTime,

                    )
                )
            }
        }

        return appInfoList
    }

    fun getAppInfo(ctx: Context, appIds: String): LocalAppProject {
        val packageManager = ctx.packageManager
        val installedPackages = packageManager.getInstalledPackages(0)
        val packages = installedPackages.find {
            appIds == it?.applicationInfo?.packageName
        }
        return packages?.let {
            LocalAppProject(
                it.applicationInfo.name,
                it.versionCode,
                it.versionName,
                android.os.Build.VERSION.RELEASE,
                it.lastUpdateTime
            )
        } ?: LocalAppProject("", 0, "", "",0)
    }


}
package com.wangzhumo.distribute.network

import android.util.Log
import com.wangzhumo.distribute.datasource.VersionInfo
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * If you have any questions, you can contact by email {wangzhumoo@gmail.com}
 *
 * @author 王诛魔 2021/12/24 10:15
 *
 * 网络请求
 */
interface AppProjectApi {

    // 获取Version的列表
    @GET("v1/info/list")
    suspend fun requestVersionList(
        @Query("last_id") last_id:Long,
        @Query("size") size:Int,
        @Query("type") type:String,
        @Query("app_id") app_id:Int,
    ):ListingResponse<VersionInfo>


    // 获取Version的详情
    @GET("v1/info/apkinfo")
    suspend fun requestAppInfo(
        @Query("id") id:Int
    ):ApiResponse<VersionInfo>

    // 下载指定版本
    @GET("v1/apk/download")
    suspend fun download(
        @Query("id") id:Int
    ): ResponseBody

    companion object {
        private const val BASE_URL = "http://distribute.wangzhumo.com/"

        fun create():AppProjectApi {
            val looger = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
                Log.d("OkHttp",it)
            })

            val client = OkHttpClient.Builder()
                .addInterceptor(looger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(AppProjectApi::class.java)
        }
    }
}
package com.wangzhumo.distribute.pages.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.wangzhumo.distribute.MainActivity
import com.wangzhumo.distribute.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import java.time.Duration
import kotlinx.coroutines.flow.flow as flow

/**
 * If you have any questions, you can contact by email {wangzhumoo@gmail.com}
 *
 * @author 王诛魔 2021/12/18 4:57
 *
 * 开屏页
 */
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        lifecycleScope.launchWhenCreated {
            flow {
                delay(1000)
                emit(1)
            }.flowOn(Dispatchers.IO)
                .collect {
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    finish()
                }
        }
    }
}
package com.ellen.kotlincoroutinestudy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    lateinit var tv:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv = findViewById(R.id.tv)
        GlobalScope.launch(Dispatchers.Main){
            //打印一下Dispatchers.Main的线程名字
            Log.e("Ellen2018","main：${Thread.currentThread().name}")

            //模仿耗时任务(example：请求网络)
            val deferred = async(Dispatchers.IO) {
                // network request
                Log.e("Ellen2018","c1：${Thread.currentThread().name}")
                delay(3000)
                a()
                "傻逼中的战斗机"
            }

            //下面的代码运行于Main线程内
            Log.e("Ellen2018","执行的代码运行的线程：${Thread.currentThread().name}")
            tv.text = deferred.await()
        }
    }

    /**
     * 2020-06-23 21:49:03.119 26556-26603/com.ellen.kotlincoroutinestudy E/Ellen2018: c1：DefaultDispatcher-worker-1
     * 2020-06-23 21:49:06.144 26556-26603/com.ellen.kotlincoroutinestudy E/Ellen2018: c2：DefaultDispatcher-worker-1
     *
     * 从以上打印的结果来看，Kotlin中的协程是基于Java Thread api实现无疑
     */

    suspend fun a(){
        withContext(Dispatchers.IO){
            Log.e("Ellen2018","c2：${Thread.currentThread().name}")
        }
    }
}
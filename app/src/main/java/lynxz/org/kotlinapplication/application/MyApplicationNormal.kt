package lynxz.org.kotlinapplication.application

import android.app.Application

import cn.jpush.android.api.JPushInterface

/**
 * Created by lynxz on 17/05/2017.
 */
class MyApplicationNormal : Application() {
    override fun onCreate() {
        super.onCreate()
        // 集成极光推送
        JPushInterface.setDebugMode(true)   // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this)           // 初始化 JPush
    }
}

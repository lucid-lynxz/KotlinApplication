package lynxz.org.kotlinapplication

import android.app.Application
import android.content.Intent
import android.content.res.AssetManager
import android.content.res.Resources
import cn.jpush.android.api.JPushInterface
import com.tencent.tinker.lib.tinker.TinkerInstaller
import com.tencent.tinker.loader.app.DefaultApplicationLike

/**
 * Created by zxz on 2016/12/28.
 * sounbus app-developer
 */
class MyApplicationLike(application: Application, tinkerFlags: Int, tinkerLoadVerifyFlag: Boolean, applicationStartElapsedTime: Long, applicationStartMillisTime: Long, tinkerResultIntent: Intent, resources: Array<Resources>, classLoader: Array<ClassLoader>, assetManager: Array<AssetManager>) : DefaultApplicationLike(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent, resources, classLoader, assetManager) {
    init {
        TinkerInstaller.install(this)
        // 集成极光推送
        JPushInterface.setDebugMode(true)    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(application)            // 初始化 JPush
    }
}

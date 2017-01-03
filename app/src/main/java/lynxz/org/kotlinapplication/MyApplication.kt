package lynxz.org.kotlinapplication

import com.tencent.tinker.loader.app.TinkerApplication
import com.tencent.tinker.loader.shareutil.ShareConstants

/**
 * Created by zxz on 2016/12/21.
 * sounbus app-developer
 */
class MyApplication : TinkerApplication(ShareConstants.TINKER_ENABLE_ALL, "lynxz.org.kotlinapplication.MyApplicationLike") {
    override fun onCreate() {
        super.onCreate()

    }
}
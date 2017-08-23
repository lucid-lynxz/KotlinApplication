package lynxz.org.kotlinapplication

import android.content.Context
import android.widget.Toast

/**
 * Created by zxz on 2016/12/21.
 * sounbus app-developer
 */
fun Context.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Context.toast(msg: Int) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

inline fun debugConf(code: () -> Unit) {
    if (BuildConfig.DEBUG) {
        code()
    }
}
package lynxz.org.kotlinapplication.activity

import android.os.Bundle
import lynxz.org.kotlinapplication.R


/**
 * 蓝牙模块功能测试
 * [蓝牙](https://developer.android.com/guide/topics/connectivity/bluetooth.html#TheBasics)
 * */
class BLEActivity : BaseActivity() {

    companion object {
        private val TAG = "BLEActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ble)

    }

}

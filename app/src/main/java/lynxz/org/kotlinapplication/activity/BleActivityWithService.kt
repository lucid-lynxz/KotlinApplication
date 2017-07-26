package lynxz.org.kotlinapplication.activity

import android.os.Bundle
import lynxz.org.kotlinapplication.R

/**
 * Created by lynxz on 31/05/2017.
 * 使用service来管理ble相关操作
 */
class BleActivityWithService : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ble)
    }
}
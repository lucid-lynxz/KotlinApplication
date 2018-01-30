package lynxz.org.kotlinapplication.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_qr_code.*
import lynxz.org.kotlinapplication.R
import lynxz.org.kotlinapplication.zxing.activity.CaptureActivity

class QrCodeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_code)

        // 二维码功能测试
        tv_scan.setOnClickListener { startActivity(Intent(this, SimpleScannerActivity::class.java)) }

        // zxing库使用测试
        tv_scan_zxing.setOnClickListener { startActivity(Intent(this, CaptureActivity::class.java)) }

    }
}

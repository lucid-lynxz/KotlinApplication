package lynxz.org.kotlinapplication

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Log
import com.tencent.tinker.lib.tinker.TinkerInstaller
import kotlinx.android.synthetic.main.activity_main.*
import lynxz.org.kotlinapplication.jpush.MainPushDemoActivity
import lynxz.org.kotlinapplication.widget.MyDialogFragment
import lynxz.org.kotlinapplication.zxing.activity.CaptureActivity

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val patchPath = Environment.getExternalStorageDirectory().absolutePath + "/patch_signed_7zip.apk"
        Log.i("tinker ", "pathPath = $patchPath")
        TinkerInstaller.onReceiveUpgradePatch(this.application, patchPath);
        tv_hello_dialog.setOnClickListener { MyDialogFragment(this@MainActivity).show(fragmentManager, "hello") }
        tv_jpush_demo.setOnClickListener { startActivity(Intent(this@MainActivity, MainPushDemoActivity::class.java)) }
        tv_scan.setOnClickListener { startActivity(Intent(this@MainActivity, SimpleScannerActivity::class.java)) }
        tv_scan_zxing.setOnClickListener { startActivity(Intent(this@MainActivity, CaptureActivity::class.java)) }
        tv_retrofit.setOnClickListener { startActivity(Intent(this@MainActivity, RetrofitDemoActivity::class.java)) }
        tv_butterknife.setOnClickListener { startActivity(Intent(this@MainActivity, ButterKnifeDemoActivity::class.java)) }

        // 测试tinker热修复
//        toast("hello,I'm patch second times ")
//        showLog()
    }

    fun showLog() {
        Log.i("tinker", "This is a patch function");
    }

    override fun onResume() {
        super.onResume()
    }
}

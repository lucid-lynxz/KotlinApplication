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

/**
 * <ol>测试tinker热修复
 *  1. 运行后会生成一个包在app/build/bakApk中,此文件为基础包;
 *  2. 然后修改代码逻辑,修改app/build.gradle中的 tinkerOldApkPath 路径
 *  3. 运行gradle-:app-tinkerPatchDebug 就可以生成一个差分包了
 *  4. 差分包在app/outputs/tinkerPatch/debug/patch_singed_7zip.apk
 *  5. 按照程序代码中设定的路径和名称,将此包拷贝过去,重新运行apk后就会加载差分包,但是要等到下次启动才会生效
 * </ol>
 * */
class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // tinker热修复差分包文件路径设定,tinker检测到差分包存在后会去加载,并在下次启动程序后生效,生效后会自动删除差分包
        val patchPath = Environment.getExternalStorageDirectory().absolutePath + "/patch_signed_7zip.apk"
        Log.i("tinker ", "pathPath = $patchPath")
        TinkerInstaller.onReceiveUpgradePatch(this.application, patchPath);

        tv_hello_dialog.setOnClickListener { MyDialogFragment(this@MainActivity).show(fragmentManager, "hello") }

        // 极光推送使用demo
        tv_jpush_demo.setOnClickListener { startActivity(Intent(this@MainActivity, MainPushDemoActivity::class.java)) }

        // 二维码功能测试
        tv_scan.setOnClickListener { startActivity(Intent(this@MainActivity, SimpleScannerActivity::class.java)) }

        // zxing库使用测试
        tv_scan_zxing.setOnClickListener { startActivity(Intent(this@MainActivity, CaptureActivity::class.java)) }

        val intent = Intent(this@MainActivity, RetrofitDemoActivity::class.java)
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        // 测试flag,添加该项后会再手机最近程序列表中出现一个新页面
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)
//        intent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
        tv_retrofit.setOnClickListener { startActivity(intent) }

        // 测试kotlin与butterknife的兼容性,发现对于butterknife的annotation还需要8.*才行
        tv_butterknife.setOnClickListener { startActivity(Intent(this@MainActivity, ButterKnifeDemoActivity::class.java)) }


        // 取消注释后,运行gradle/tinkerPatchDebug,测试tinker功能
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

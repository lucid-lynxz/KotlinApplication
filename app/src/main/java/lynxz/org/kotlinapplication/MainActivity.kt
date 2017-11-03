package lynxz.org.kotlinapplication

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import com.tencent.tinker.lib.tinker.TinkerInstaller
import kotlinx.android.synthetic.main.activity_main.*
import lynxz.org.kotlinapplication.TextureViewDemo.LiveCameraDemo
import lynxz.org.kotlinapplication.activity.*
import lynxz.org.kotlinapplication.jpush.MainPushDemoActivity
import lynxz.org.kotlinapplication.util.Logger
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

        testTablayout()
        Logger.d("test tinker")
        Logger.d("test tinker2")

        debugConf {
            toast("现在处于 debug 模式")
        }

        // tinker好像跟camera.open冲突
        // tinker热修复差分包文件路径设定,tinke r检测到差分包存在后会去加载,并在下次启动程序后生效,生效后会自动删除差分包
        val patchPath = Environment.getExternalStorageDirectory().absolutePath + "/patch_signed_7zip.apk"
        Log.i("tinker ", "pathPath = $patchPath")
        TinkerInstaller.onReceiveUpgradePatch(this.application, patchPath)

        tv_hello_dialog.setOnClickListener { MyDialogFragment(this@MainActivity).show(fragmentManager, "hello") }

        tv_anko_demo.setOnClickListener { startActivity(Intent(this@MainActivity, AnkoDemoActivity::class.java)) }

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

        tv_bluetoothh.setOnClickListener { startActivity(Intent(this@MainActivity, BluetoothActivity::class.java)) }
        tv_bluetooth_low_energy.setOnClickListener { startActivity(Intent(this@MainActivity, BLEActivity::class.java)) }

        // 测试textureView的简单使用,跟tinker冲突了,会造成程序崩溃,就不在这里测试了,保留代码是为了以后有需要的话复现
        tv_textureview_livecamera.setOnClickListener { startActivity(Intent(this, LiveCameraDemo::class.java)) }

        // 测试通过webview调起app功能
        tv_webview.setOnClickListener { startActivity(Intent(this, WebviewActivity::class.java)) }

        tv_navigation_view.setOnClickListener { startActivity(Intent(this, NavigationViewActivity::class.java)) }

        tv_animation_demo.setOnClickListener { startActivity(Intent(this, AnimationActivity::class.java)) }

        tv_baz_view.setOnClickListener { startActivity(Intent(this, BazDemoActivity::class.java)) }

        tv_pop_window.setOnClickListener { showPopWindow() }
        // 取消注释后,运行gradle/tinkerPatchDebug,测试tinker功能
//        toast("hello,I'm patch second times ")
//        showLog()

        // 测试格式化日志扩展类
        Logger.init(Logger.debugLevel, MainActivity::class.java)
        val s = "{\"firstName\":\"Brett\",\"lastName\":\"McLaughlin\",\"email\":\"aaaa\"}"
        Logger.json(s)
    }

    private fun showPopWindow() {
        val contentView = layoutInflater.inflate(R.layout.pop_window_layout, null)
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        val popWindow = PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
            setBackgroundDrawable(BitmapDrawable())
            isOutsideTouchable = true
            isFocusable = true
        }
        showPopAtViewTop(popWindow, tv_pop_window)
    }

    private fun showPopAtViewTop(popupWindow: PopupWindow, v: View) {
        val location = IntArray(2)
        v.getLocationOnScreen(location)
        val x = location[0]
        val y = location[1]
        // 初次显示需要measure一下,或者height会为0
        popupWindow.contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        val height = popupWindow.contentView.measuredHeight

        val offY = y - height
        Logger.d("height = $height y = $y all = $offY")
        popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, x, offY)
    }


    // 测试tablayout功能
    private fun testTablayout() {
        var largeTab = layoutInflater.inflate(R.layout.tab_large, null, false)
        val tvHolder = largeTab.findViewById(R.id.tv_place)
        tvHolder.visibility = View.GONE
        var normalTab1 = layoutInflater.inflate(R.layout.tab_large, null, false)
        var normalTab2 = layoutInflater.inflate(R.layout.tab_large, null, false)
        var normalTab3 = layoutInflater.inflate(R.layout.tab_large, null, false)
        var normalTab4 = layoutInflater.inflate(R.layout.tab_large, null, false)


        tl_main.addTab(tl_main.newTab().setCustomView(normalTab1))
        tl_main.addTab(tl_main.newTab().setCustomView(normalTab2))
        tl_main.addTab(tl_main.newTab().setCustomView(largeTab))
        tl_main.addTab(tl_main.newTab().setCustomView(normalTab3))
        tl_main.addTab(tl_main.newTab().setCustomView(normalTab4))


    }

    fun showLog() {
        Log.i("tinker", "This is a patch function");
    }

    override fun onResume() {
        super.onResume()
    }
}

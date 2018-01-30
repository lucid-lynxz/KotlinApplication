package lynxz.org.kotlinapplication.activity

import android.content.ComponentName
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.webkit.*
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import kotlinx.android.synthetic.main.activity_webview.*
import lynxz.org.kotlinapplication.R
import lynxz.org.kotlinapplication.util.Logger
import java.net.URL


/**
 * Created by lynxz on 19/05/2017.
 * 测试通过webview启动app
 */
class WebviewActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        initWebview()

        val urlArr = resources.getStringArray(R.array.app_urls)
        spn_urls.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>, view: View,
                                        position: Int, id: Long) {

                edt_url.setText(urlArr[position])
            }
        }

        btn_load_url.setOnClickListener { loadUrlOrStartApp(edt_url.text.toString()) }
        btn_start_wx.setOnClickListener {
            try {
                /*
                * 直接指定intent可能会抛出异常
                * 需要目标页面添加 android:exported="true"
                * 参考: https://stackoverflow.com/questions/4162447/android-java-lang-securityexception-permission-denial-start-intent
                * */
                val intent = Intent(Intent.ACTION_VIEW)
                val cmp = ComponentName("com.tencent.mm", "com.tencent.mm.plugin.scanner.ui.BaseScanUI")
//            intent.action = Intent.ACTION_MAIN
//            intent.addCategory(Intent.CATEGORY_LAUNCHER)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.component = cmp
                startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * webview相关设置
     * */
    private fun initWebview() {
        // 启用webview调试功能,发布时删除
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true)
        }

        with(wv) {
            clearCache(true)
            loadUrl("about:blank")

            with(settings) {
                javaScriptEnabled
                domStorageEnabled
                databaseEnabled
                useWideViewPort = true
                loadWithOverviewMode = true
                cacheMode = WebSettings.LOAD_DEFAULT
                layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN

                javaScriptCanOpenWindowsAutomatically
                allowFileAccess
                userAgentString = "$userAgentString demoAgent"
            }

            setWebChromeClient(object : WebChromeClient() {
                override fun onReceivedTitle(view: WebView?, title: String?) {
                    super.onReceivedTitle(view, title)
                    Logger.d("title is $title")
                }
            })

            setWebViewClient(object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                    return super.shouldOverrideUrlLoading(view, request)
                }

                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
//                    return super.shouldOverrideUrlLoading(view, url)
                    Logger.d("url: $url", "webview")
                    if (url != null) {
                        if (url.contains("platformapi/startapp")) run {
                            startApp(url)
                            // android  6.0 两种方式获取intent都可以跳转支付宝成功,7.1测试不成功
                        } else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M && url.contains("platformapi") && url.contains("startapp")) {
                            startApp(url)
                        } else {
                            if (url.startsWith("http")) {
                                loadUrlOrStartApp(url)
                            } else {
                                startApp(url)
                            }
                        }
                    }
                    return true
                }
            })
        }
    }

    /**
     * 启动app
     * */
    private fun startApp(url: String) {
        val intent: Intent
        try {
            Logger.d("要启动app的url: $url", "webview")
            intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
            intent.addCategory(Intent.CATEGORY_BROWSABLE)
            intent.component = null
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
            Logger.d("error:\n${e.message}")
        }
    }

    private fun loadUrlOrStartApp(url: String?) {
        if (TextUtils.isEmpty(url)) {
            return
        }
//        startApp(url!!)
        val urlObj = URL(url)
        if (urlObj.host.equals("weixin.qq.com", true)
                || urlObj.host.equals("qm.qq.com", true)
                || urlObj.host.equals("weibo.cn", true)) {
            startApp(url!!)
        } else {
            wv.loadUrl(url)
        }
    }
}
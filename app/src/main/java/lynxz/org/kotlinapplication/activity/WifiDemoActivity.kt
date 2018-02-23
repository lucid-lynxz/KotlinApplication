package lynxz.org.kotlinapplication.activity

import android.annotation.SuppressLint
import android.content.Context
import android.net.wifi.WifiManager
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_wifi_demo.*
import lynxz.org.kotlinapplication.R
import lynxz.org.kotlinapplication.toast
import lynxz.org.kotlinapplication.util.WifiAutoConnectManager

class WifiDemoActivity : BaseActivity() {

    val wifiSSID = "Soundbus-Office-5G"
    val pwd = "sound318"

    @SuppressLint("WifiManagerLeak")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wifi_demo)
        val wifiManager: WifiManager? = getSystemService(Context.WIFI_SERVICE) as WifiManager?
        val wifiAutoConnectManager = WifiAutoConnectManager(wifiManager, application)

        tv_get_wifi_info.setOnClickListener {
            tv_info.text = wifiAutoConnectManager.getAllWifiInfo()
        }

        tv_remove_specify_wifi.setOnClickListener {
            wifiManager?.configuredNetworks?.firstOrNull { it.SSID == "\"$wifiSSID\"" }?.let {
                val result = wifiAutoConnectManager.removeWifi(it.SSID)
                toast("removeWifi ${it.SSID} $result")
            }
        }

        tv_remove_first_wifi.setOnClickListener {
            wifiManager?.configuredNetworks?.firstOrNull { true }?.let {
                val result = wifiAutoConnectManager.removeWifi(it.SSID)
                toast("removeWifi ${it.SSID} $result")
            }
        }

        tv_create_wifi_config.setOnClickListener {
            wifiAutoConnectManager.connect(wifiSSID, pwd, WifiAutoConnectManager.WifiCipherType.WIFICIPHER_WPA)
            //            wifiAutoConnectManager.connect("Soundbus-office", "sound318", WifiAutoConnectManager.WifiCipherType.WIFICIPHER_WPA)
//            wifiAutoConnectManager.connect("Soundbus-Office-5G", "sound318", WifiAutoConnectManager.WifiCipherType.WIFICIPHER_WPA)
        }
    }
}

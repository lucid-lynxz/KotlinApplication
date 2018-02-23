package lynxz.org.kotlinapplication.util

import android.app.Application
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import lynxz.org.kotlinapplication.otherwise
import lynxz.org.kotlinapplication.yes
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Proxy

/**
 * Created by lynxz on 23/02/2018.
 * 博客: https://juejin.im/user/5812c2b0570c3500605a15ff
 * 连接指定的wifi,[参考文章](http://www.cnblogs.com/zhuqiang/p/3566686.html)
 */
class WifiAutoConnectManager(var wifiManager: WifiManager?, var mApp: Application) {
    companion object {
        val TAG = WifiAutoConnectManager::class.java.simpleName
    }

    // 定义几种加密方式，一种是WEP，一种是WPA，还有没有密码的情况
    enum class WifiCipherType {
        WIFICIPHER_WEP, WIFICIPHER_WPA, WIFICIPHER_NOPASS, WIFICIPHER_INVALID
    }

    /**
     * 连接某个wifi
     * */
    fun connect(ssid: String, pwd: String, wifiCipherType: WifiCipherType) {
        Thread(ConnectRunnable(ssid, pwd, wifiCipherType)).start()
    }

    /**
     * 某个ssid wifi是否已经存在
     * 若存在,则返回其 WifiConfiguration
     * 否则,返回null
     * */
    fun isExist(ssid: String): WifiConfiguration? {
        val rSsid = ssid.replace("\"", "")
        return wifiManager?.configuredNetworks?.firstOrNull {
            //            Logger.d("ssid = ${it.SSID}")
            it.SSID == "\"$rSsid\""
        }
    }

    fun createWifiConfiguration(ssid: String, pwd: String, wifiCipherType: WifiCipherType): WifiConfiguration {
        return WifiConfiguration().apply {
            allowedAuthAlgorithms.clear()
            allowedGroupCiphers.clear()
            allowedKeyManagement.clear()
            allowedPairwiseCiphers.clear()
            allowedProtocols.clear()
            SSID = "\"$ssid\""

            when (wifiCipherType) {
                WifiCipherType.WIFICIPHER_NOPASS -> {
                    wepKeys[0] = ""
                    wepTxKeyIndex = 0
                    allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE)
                }
                WifiCipherType.WIFICIPHER_WEP -> {
                    pwd.isNotEmpty().yes {
                        if (isHexWepKey(pwd)) {
                            wepKeys[0] = pwd
                        } else {
                            wepKeys[0] = "\"$pwd\""
                        }

                        allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN)
                        allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED)
                        allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE)
                        wepTxKeyIndex = 0
                    }
                }
                WifiCipherType.WIFICIPHER_WPA -> {
                    preSharedKey = "\"$pwd\""
                    hiddenSSID = true
                    allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN)
                    allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP)
                    allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK)
                    allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP)

                    // 此处需要修改否则不能自动重联
                    // config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
                    allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP)
                    allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP)
                    status = WifiConfiguration.Status.ENABLED
                }
            }
        }
    }

    /**
     * 创建保存某个wifi的configuration信息,并进行连接
     * */
    inner class ConnectRunnable(var ssid: String, var pwd: String, var wifiCipherType: WifiCipherType = WifiCipherType.WIFICIPHER_WEP) : Runnable {
        override fun run() {
            openWifi()
            // 开启wifi功能需要一段时间(我在手机上测试一般需要1-3秒左右)，所以要等到wifi
            // 状态变成WIFI_STATE_ENABLED的时候才能执行下面的语句
            while (wifiManager?.wifiState == WifiManager.WIFI_STATE_ENABLING) {
                try {
                    Thread.sleep(100);
                } catch (ie: InterruptedException) {
                    ie.printStackTrace()
                }
            }

            var wifiConfig: WifiConfiguration? = createWifiConfiguration(ssid, pwd, wifiCipherType)
            val removeSuccess = removeWifi(ssid)
            var netId = wifiManager?.addNetwork(wifiConfig) ?: -1
            if (!removeSuccess) {
                wifiConfig = isExist(ssid)
                netId = wifiConfig?.networkId ?: -1
            }

            val enabled = wifiManager?.enableNetwork(netId, true)
            val connected = wifiManager?.reconnect()
            Logger.d("$netId\t$enabled\t$connected", TAG)
        }
    }

    /**
     * 移除已保存的某个wifi信息
     * 测试发现在android 7.0+上无法删除非本应用创建的wifi(无6.0的机子,无法验证),报错信息如下:
     *  UID 10002 does not have permission to delete configuration "xxxWifi"WPA_PSK
     * */
    fun removeWifi(ssid: String?): Boolean {
        ssid.isNullOrEmpty().yes {
            return false
        }.otherwise {
            val tempConfig = isExist(ssid!!)
            return if (tempConfig != null) {
                val result = wifiManager?.removeNetwork(tempConfig.networkId) ?: false
//                if (!result) {
//                    forgetWifi(tempConfig)
//                }
                result
            } else {
                false
            }
        }
    }

    /**
     * 他通过反射调用hide函数 forget(int,ActionListener) 来删除wifi
     * 不过我在android7.0+上也是测试不成功
     * */
    private fun forgetWifi(tempConfig: WifiConfiguration) {
        val actionListener = Class.forName("android.net.wifi.WifiManager\$ActionListener")
        //实现 InvocationHandler 接口，所有的我们需要的回调方法，都是通过这个代理回调来实现的
        //通过第二个参数可以判断回调的是什么方法
        val invocationHandler = InvocationHandler { proxy, method, args ->
            Logger.d("forgetWifi 代理回调 ${method.name}： ${args?.joinToString()}")
            //通过方法的名称判断回调的是什么方法
            // if (method.name == "onSuccess") {
            // } else {
            // }
        }
        //创建接口代理类，可以理解这个就是接口实现类（实现了接口 ActionListener 的类）
        val proxy = Proxy.newProxyInstance(mApp.classLoader, arrayOf(actionListener), invocationHandler)

        val forgetNet = wifiManager?.javaClass?.getDeclaredMethod("forget", Int::class.java, actionListener)
        forgetNet?.isAccessible = true
        forgetNet?.invoke(wifiManager, tempConfig.networkId, proxy)
    }

    /**
     * 打开wifi
     * */
    private fun openWifi(): Boolean {
        wifiManager?.isWifiEnabled = true
        return wifiManager?.isWifiEnabled ?: false
    }

    private fun isHexWepKey(wepKey: String): Boolean {
        val len = wepKey.length

        // WEP-40, WEP-104, and some vendors using 256-bit WEP (WEP-232?)
        return if (len != 10 && len != 26 && len != 58) {
            false
        } else isHex(wepKey)

    }

    private fun isHex(key: String): Boolean {
        return (key.length - 1 downTo 0)
                .map { key[it] }
                .any {
                    (it in '0'..'9' || it in 'A'..'F'
                            || it in 'a'..'f')
                }
    }

    fun getAllWifiInfo(): String {
        val sb = StringBuilder(1000)
        wifiManager?.configuredNetworks?.forEach {
            sb.append(it.SSID).append("-").append(it.status).append("-")
                    .append(it.wepTxKeyIndex).append("-").append(it.wepKeys[0]).append("\n")
        }
        return sb.toString()
    }
}
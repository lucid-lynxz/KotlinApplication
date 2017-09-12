package lynxz.org.kotlinapplication.activity

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_ble.*
import lynxz.org.kotlinapplication.R
import lynxz.org.kotlinapplication.adapter.RvBleAdapter
import lynxz.org.kotlinapplication.util.Logger
import org.jetbrains.anko.toast
import java.util.*


/**
 * 蓝牙模块功能测试
 * [蓝牙](https://developer.android.com/guide/topics/connectivity/bluetooth.html#TheBasics)
 * 在android5.x上需要开启gps定位和蓝牙功能才能搜索得到列表
 * */
class BLEActivity : BaseActivity() {
    companion object {
        private val TAG = "BLEActivity"
    }

    val bleAddressSet = HashSet<String>()
    var mBluetoothAdapter: BluetoothAdapter? = null
    var mBluetoothLEScanner: BluetoothLeScanner? = null
    var supportLe = false
    var devices = mutableListOf<BluetoothDevice>()
    val rxPermissions: RxPermissions by lazy { RxPermissions(this) }
    val mHandler by lazy { Handler() }

    val mScanCallback = BluetoothAdapter.LeScanCallback { device, rssi, scanRecord ->
        addDevice(device)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ble)
        initRecyclerView()
        checkSupport()

        rxPermissions.request(android.Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe({
                    granted ->
                    Logger.d("定位权限获取结果: $granted")
                })

        btn_stop.setOnClickListener {
            stopScan()
        }

        btn_scan.setOnClickListener {
            // 不要一直搜索,设定一个延时自动停止扫描
            mHandler.postDelayed({ stopScan() }, 10000)
            // android api21以上时,使用BluetoothLEScanner来搜索低功耗蓝牙设备,
            if (mBluetoothLEScanner != null) {
                Logger.d("use mBluetoothLEScanner to startScan")
                mBluetoothLEScanner?.startScan(mLeScanCallback)
            } else {
                // android 18(4.3)以上时,使用BluetoothAdapter来扫描
                if (supportLe) {
                    Logger.d("use mBluetoothAdapter to startLeScan")
                    mBluetoothAdapter?.startLeScan(mScanCallback)
                } else {
                    Logger.d("cannot startScan")
                }
            }
        }
    }

    private fun initRecyclerView() {
        rv_ble.layoutManager = LinearLayoutManager(this)
        rv_ble.adapter = RvBleAdapter(this, devices)
    }

    private fun addDevice(device: BluetoothDevice?) {
        if (device == null) {
            return
        }

        if (!bleAddressSet.contains(device.address)) {
            bleAddressSet.add(device.address)
            Logger.d("${device.address}\t${device.name}\t${device.type}", "scanResult")

            devices.add(device)
            rv_ble.adapter.notifyDataSetChanged()
        }
    }

    val mLeScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult?) {
            super.onScanResult(callbackType, result)
            addDevice(result?.device)
        }

        override fun onBatchScanResults(results: MutableList<ScanResult>?) {
            super.onBatchScanResults(results)
            results?.forEach { addDevice(it.device) }
        }

        override fun onScanFailed(errorCode: Int) {
            super.onScanFailed(errorCode)
            Logger.e("$errorCode")
        }
    }

    /**
     * 停止搜索蓝牙设备
     * */
    private fun stopScan() {
        if (mBluetoothLEScanner != null) {
            Logger.d("use mBluetoothLEScanner to stopScan")
            mBluetoothLEScanner?.stopScan(mLeScanCallback)
        } else {
            if (supportLe) {
                Logger.d("use mBluetoothAdapter to stopLeScan")
                mBluetoothAdapter?.stopLeScan(mScanCallback)
            }
        }
        toast("停止扫描成功...")
    }

    override fun onResume() {
        super.onResume()
        // 检查启用蓝牙
        if (!(mBluetoothAdapter?.isEnabled ?: true)) {
            mBluetoothAdapter?.enable()
        }
    }

    /**
     * 检测设备是否支持 Bluetooth 和 Bluetooth low energy
     */
    fun checkSupport() {
        //因为在清单文件中设置App适用于不支持BLE的设备，这里需要对不支持BLE的设备进行相应处理
        if (!packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, R.string.ble_not_support, Toast.LENGTH_SHORT).show()
            finish()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            val bm = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
            mBluetoothAdapter = bm.adapter
            supportLe = true
        }

        if (mBluetoothAdapter == null) { //设备不支持蓝牙
            Toast.makeText(this, R.string.ble_not_support, Toast.LENGTH_SHORT).show()
            finish()
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBluetoothLEScanner = mBluetoothAdapter?.bluetoothLeScanner
        }
    }

}

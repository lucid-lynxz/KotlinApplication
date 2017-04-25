package lynxz.org.kotlinapplication

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import lynxz.org.kotlinapplication.util.Logger


/**
 * 蓝牙模块功能测试
 * [蓝牙](https://developer.android.com/guide/topics/connectivity/bluetooth.html#TheBasics)
 * */
class BluetoothActivity : BaseActivity() {

    var defaultAdapter: BluetoothAdapter? = null
    var adapterList = mutableListOf<String>() // 保存已配对的设备
    val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)

    companion object {
        private val TAG = "BluetoothActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bluetooth_actviity)

        // 注册蓝牙发现设备后的广播监听
        // Register the BroadcastReceiver
        startDiscoveryBluetoothDevices()

        defaultAdapter = BluetoothAdapter.getDefaultAdapter()
        if (defaultAdapter == null) {
            // Device does not support Bluetooth
            toast("不支持蓝牙")
        } else {
            // 若未启用蓝牙,则弹出启用蓝牙对话框
//            defaultAdapter?.enable()
            if (!(defaultAdapter?.isEnabled ?: false)) {
                startActivityForResult(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), 99)
            } else {
                getPairedDevices()
                setCanBeDiscovery()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        toast("requestCode = $requestCode , resultCode  = $resultCode ${Activity.RESULT_OK}")
        if (requestCode == 99
                && resultCode == Activity.RESULT_OK) {
            getPairedDevices()
            setCanBeDiscovery()
        }
    }

    /**
     * 查询已配对蓝牙设备
     * */
    private fun getPairedDevices() {
        val bondedDevices = defaultAdapter?.bondedDevices // Set<BluetoothDevice>
        val size = bondedDevices?.size ?: 0
        if (size > 0) { // 存在已配对设备
            bondedDevices?.map { adapterList.add("${it.name}\t${it.address}") }
            adapterList.map { Logger.d(it, TAG) }
        } else {
            toast("不存在已配对的设备")
        }
    }

    /**
     * Create a BroadcastReceiver for ACTION_FOUND
     * 用于发现蓝牙设备,注册广播接收器,发现新设备后做记录
     */
    private val mReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND == action) {
                // Get the BluetoothDevice object from the Intent
                val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                // Add the name and address to an array adapter to show in a ListView
                adapterList.add("${device.name}\t${device.address}")
                if (device.name.equals("Nexus 6P", true)) {
                    Logger.d("$intent")
                    Logger.d("${device.name} extra_data is : ${intent.extras["extra_data"]}", TAG)
                }
            }
        }
    }

    /**
     * 发现附近的蓝牙设备
     *
     * note: 执行设备发现对于蓝牙适配器而言是一个非常繁重的操作过程，并且会消耗大量资源。
     * 在找到要连接的设备后，确保始终使用 cancelDiscovery() 停止发现，然后再尝试连接。
     * 此外，如果您已经保持与某台设备的连接，那么执行发现操作可能会大幅减少可用于该连接的带宽，
     * 因此不应该在处于连接状态时执行发现操作。
     * */
    private fun startDiscoveryBluetoothDevices() {
        registerReceiver(mReceiver, filter) // Don't forget to unregister during onDestroy
    }

    /**
     * 启用设备的可检测性,可被其他设备发现,这里设定默认可检测时间为300秒
     *
     *  默认情况下，设备将变为可检测到并持续 120 秒钟。
     *  可以通过添加 EXTRA_DISCOVERABLE_DURATION Intent Extra 来定义不同的持续时间。
     *  应用可以设置的最大持续时间为 3600 秒，值为 0 则表示设备始终可检测到。
     *  任何小于 0 或大于 3600 的值都会自动设为 120 秒
     * */
    private fun setCanBeDiscovery() {
        val discoverableIntent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE)
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300)
        discoverableIntent.putExtra("extra_data", "hello")
        startActivity(discoverableIntent)
    }


    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mReceiver)
    }

}

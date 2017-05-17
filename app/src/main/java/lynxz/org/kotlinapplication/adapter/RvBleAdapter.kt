package lynxz.org.kotlinapplication.adapter

import android.bluetooth.BluetoothDevice
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import lynxz.org.kotlinapplication.R
import org.jetbrains.anko.find

/**
 * Created by lynxz on 17/05/2017.
 */
class RvBleAdapter(val mCxt: Context, var devices: MutableList<BluetoothDevice>) : RecyclerView.Adapter<RvBleAdapter.BleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BleViewHolder {
        return BleViewHolder(View.inflate(mCxt, R.layout.item_rv_ble, parent))
    }

    override fun onBindViewHolder(holder: BleViewHolder?, position: Int) {
        val device = devices[position]
        holder?.tv?.text = "${device.address}\n${device.name}"
    }

    override fun getItemCount() = devices.size


    inner class BleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv: TextView = itemView.find(R.id.tv_info)
    }
}
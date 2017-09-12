package lynxz.org.kotlinapplication.activity

import android.app.Activity
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import com.google.zxing.Result
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_scan_qr.*
import lynxz.org.kotlinapplication.R
import lynxz.org.kotlinapplication.toast
import me.dm7.barcodescanner.zxing.ZXingScannerView

//import pub.devrel.easypermissions.AfterPermissionGranted
//import pub.devrel.easypermissions.EasyPermissions

/**
 * 测试该二维码项目,之前在项目中发现该改第三方库在华为部分双摄像头手机上会出现屏幕画面虚化模糊的问题
 * 测试后发现,view所在的父容器都得是frameLayout才行
 * */
class SimpleScannerActivity : Activity(), ZXingScannerView.ResultHandler {
    private var mScannerView: ZXingScannerView? = null
    val rxPermission: RxPermissions by lazy {
        RxPermissions(this)
    }

    companion object {
        const val CAMERA_PERMISSION = 200
    }

    public override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        setContentView(R.layout.activity_scan_qr)
        mScannerView = ZXingScannerView(this)   // Programmatically initialize the scanner view
//        setContentView(mScannerView)                // Set the scanner view as the content view
        ll_container.addView(mScannerView)
    }

    public override fun onResume() {
        super.onResume()
        mScannerView!!.setResultHandler(this) // Register ourselves as a handler for scan results.
        startCamera()
    }

    fun startCamera() {
        rxPermission
                .request(android.Manifest.permission.CAMERA)
                .subscribe { granted ->
                    if (granted) { // Always true pre-M
                        mScannerView?.startCamera()
                    } else {
                        toast("请允许相机权限")
                    }
                }
    }

    public override fun onPause() {
        super.onPause()
        mScannerView?.stopCamera()           // Stop camera on pause
    }

    override fun handleResult(rawResult: Result) {
        // Do something with the result here
        Log.v(TAG, rawResult.text) // Prints scan results
        Log.v(TAG, rawResult.barcodeFormat.toString()) // Prints the scan format (qrcode, pdf417 etc.)

        toast("识别到的二维码是: ${rawResult.text}")
        // If you would like to resume scanning, call this method below:
//        mScannerView!!.resumeCameraPreview(this)
    }
}
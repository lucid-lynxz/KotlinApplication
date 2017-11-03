package lynxz.org.kotlinapplication.activity

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import kotlinx.android.synthetic.main.activity_pop_window.*
import lynxz.org.kotlinapplication.R
import lynxz.org.kotlinapplication.util.Logger

/**
 * Created by lynxz on 03/11/2017.
 */
class PopWindowActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pop_window)
        btn_show.setOnClickListener { showPopWindow() }
    }

    private fun showPopWindow() {
        val contentView = layoutInflater.inflate(R.layout.pop_window_layout, null)
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        val popWindow = PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
            setBackgroundDrawable(BitmapDrawable())
            isOutsideTouchable = true
            isFocusable = true
            setOnDismissListener { edt_input.requestFocus() }
        }
        showPopAtViewTop(popWindow, btn_show)
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

}
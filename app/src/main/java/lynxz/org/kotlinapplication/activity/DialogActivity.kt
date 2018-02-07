package lynxz.org.kotlinapplication.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_dialog.*
import lynxz.org.kotlinapplication.R


class DialogActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog)

        tv_show_pop_window.setOnClickListener { startActivity(Intent(this, PopWindowActivity::class.java)) }

        tv_bottom_dialog.setOnClickListener { showBottomDialog() }
    }

    private fun showBottomDialog() {
        // 从底部弹出对话框,支持向上拖动
        // 如果没有设定 behavior 就不需要 Coordinator Layout 布局
        // 参考: http://blog.csdn.net/yanzhenjie1003/article/details/51938400
        BottomSheetDialog(this).apply {
            setContentView(layoutInflater.inflate(R.layout.list_bottom, null))
            show()
        }
    }
}

package lynxz.org.kotlinapplication.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import lynxz.org.kotlinapplication.R

/**
 * 自定义圆弧进度条
 * */
class ArcProgressActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arc_progress)
    }
}

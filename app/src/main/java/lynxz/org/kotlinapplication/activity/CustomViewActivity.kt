package lynxz.org.kotlinapplication.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_custom_view.*
import lynxz.org.kotlinapplication.R

class CustomViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_view)

        tv_baz_view.setOnClickListener { startActivity(Intent(this, BazDemoActivity::class.java)) }
        tv_arc_view.setOnClickListener { startActivity(Intent(this, ArcProgressActivity::class.java)) }

    }
}

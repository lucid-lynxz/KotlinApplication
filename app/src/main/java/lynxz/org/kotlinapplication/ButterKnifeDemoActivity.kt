package lynxz.org.kotlinapplication

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import butterknife.BindColor
import butterknife.BindView
import butterknife.ButterKnife

/**
 * Created by zxz on 2016/12/29.
 * sounbus app-developer
 */
class ButterKnifeDemoActivity : BaseActivity() {
    @BindView(R.id.tv)
    lateinit var tv: TextView

    @BindView(R.id.btn)
    lateinit var btn: Button

    @JvmField
    @BindColor(R.color.main_text_color)
    var mainTextColor: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_butterknife)
        ButterKnife.bind(this)
    }

    override fun onResume() {
        super.onResume()
        toast("butterknife tv = $tv  color = $mainTextColor")
        tv.text = mainTextColor.toString()
    }
}
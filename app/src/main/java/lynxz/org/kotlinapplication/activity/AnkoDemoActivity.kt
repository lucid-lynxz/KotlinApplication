package lynxz.org.kotlinapplication.activity

import android.os.Bundle
import android.view.View
import lynxz.org.kotlinapplication.widget.LoginUi
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

/**
 * 出自: https://github.com/xiehui999/KotlinForAndroid
 * */
class AnkoDemoActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_anko_demo)
        LoginUi().setContentView(this@AnkoDemoActivity)
    }




    private fun click() {
/*        toast("Hello, ${name.text}!")
        alert("我是Dialog") {
            yesButton { toast("yes") }
            noButton { toast("no") }
        }.show()*/
        doAsync {
            Thread.sleep(3000)
            uiThread { toast("线程${Thread.currentThread().name}") }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {

        }
    }
}

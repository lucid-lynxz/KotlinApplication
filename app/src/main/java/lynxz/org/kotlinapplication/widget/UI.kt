package lynxz.org.kotlinapplication.widget

import android.view.View
import lynxz.org.kotlinapplication.R
import lynxz.org.kotlinapplication.activity.AnkoDemoActivity
import lynxz.org.kotlinapplication.util.Logger
import org.jetbrains.anko.*

class UI : AnkoComponent<AnkoDemoActivity> {
    override fun createView(ui: AnkoContext<AnkoDemoActivity>): View {
        return with(ui) {
            verticalLayout {
                val textView = textView("我是一个TextView") {
                    textSize = sp(17).toFloat()
                    textColor = context.resources.getColor(R.color.red)
                }.lparams {
                    margin = dip(10)
                    height = dip(40)
                    width = matchParent
                }
                val name = editText("EditText")
                button("Button") {
                    setOnClickListener { click() }
                }
            }
        }
    }

    private fun click() {
/*        toast("Hello, ${name.text}!")
        alert("我是Dialog") {
            yesButton { toast("yes") }
            noButton { toast("no") }
        }.show()*/
        doAsync {
            Thread.sleep(3000)
            uiThread { Logger.d("线程${Thread.currentThread().name}") }
        }
    }
}
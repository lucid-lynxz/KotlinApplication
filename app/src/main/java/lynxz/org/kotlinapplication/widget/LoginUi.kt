package lynxz.org.kotlinapplication.widget

import android.graphics.Color
import android.view.Gravity
import android.widget.EditText
import android.widget.LinearLayout
import lynxz.org.kotlinapplication.R
import lynxz.org.kotlinapplication.activity.AnkoDemoActivity
import org.jetbrains.anko.*

class LoginUi : AnkoComponent<AnkoDemoActivity> {
    lateinit var et_account: EditText
    lateinit var et_password: EditText
    val edt_account = 1

    override fun createView(ui: AnkoContext<AnkoDemoActivity>) = with(ui) {
        verticalLayout {
            backgroundColor = context.resources.getColor(android.R.color.white)
            gravity = Gravity.CENTER_HORIZONTAL
            imageView(R.mipmap.ic_launcher).lparams {
                width = dip(100)
                height = dip(100)
                topMargin = dip(64)
            }

            linearLayout {
                gravity = Gravity.CENTER_VERTICAL
                orientation = LinearLayout.HORIZONTAL
                backgroundResource = R.drawable.bg_frame_corner
                imageView {
                    image = resources.getDrawable(R.mipmap.ic_username)
                }.lparams(width = wrapContent, height = wrapContent) {
                    leftMargin = dip(12)
                    rightMargin = dip(15)
                }
                et_account = editText {
                    id = edt_account
                    setHint(R.string.user_name)
//                        hint =getString(R.string.user_name)
                    hintTextColor = Color.parseColor("#666666")
                    textSize = 16f
                    background = null
                }.lparams(width = matchParent)
            }.lparams(width = dip(300), height = dip(40)) {
                topMargin = dip(45)
            }

            linearLayout {
                orientation = LinearLayout.HORIZONTAL
                backgroundResource = R.drawable.bg_frame_corner
                gravity = Gravity.CENTER_VERTICAL
                imageView {
                    image = resources.getDrawable(R.mipmap.ic_password)
                }.lparams {
                    leftMargin = dip(12)
                    rightMargin = dip(15)
                }
                et_password = editText {
                    hintResource = R.string.login_password
//                        setHint(R.string.login_password)
                    hintTextColor = Color.parseColor("#666666")
                    textSize = 16f
                    background = null
                }.lparams(width = matchParent)
            }.lparams {
                width = dip(300)
                height = dip(40)
                topMargin = dip(10)

            }

            button("登录") {
                gravity = Gravity.CENTER
                background = resources.getDrawable(R.drawable.bg_login_btn)
                textColor = Color.parseColor("#ffffff")
                onClick {
                    if (et_account.text.toString().isNotEmpty()
                            && et_password.text.toString().isNotEmpty())
                    //startActivity<MainActivity>("account" to et_account.text.toString(), "password" to et_password.text.toString())
                        toast("登录成功...")
                    else
                        toast("请输入账户或者密码")
                }
            }.lparams(width = dip(300), height = dip(44)) {
                topMargin = dip(18)
            }
            linearLayout {
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.CENTER_VERTICAL
                checkBox("记住密码") {
                    textColor = Color.parseColor("#666666")
                    textSize = 16f
                    leftPadding = dip(5)
                }
                textView("隐私协议") {
                    textColor = Color.parseColor("#1783e3")
                    gravity = Gravity.RIGHT
                    textSize = 16f
                }.lparams(width = matchParent)
            }.lparams(width = dip(300)) {
                topMargin = dip(18)
            }

            textView("Copyright © Code4Android") {
                textSize = 14f
                gravity = Gravity.CENTER or Gravity.BOTTOM

            }.lparams {
                bottomMargin = dip(35)
                weight = 1f
            }
        }
    }
}
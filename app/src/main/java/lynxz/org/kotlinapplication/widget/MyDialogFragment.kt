package lynxz.org.kotlinapplication.widget

import android.app.DialogFragment
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.layout_dialog.*
import lynxz.org.kotlinapplication.Main2Activity
import lynxz.org.kotlinapplication.R

/**
 * Created by zxz on 2016/12/16.
 * sounbus app-developer
 */
class MyDialogFragment(var mCxt: Context? = null) : DialogFragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.layout_dialog, null)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tv_tip.setOnClickListener { startActivity(Intent(mCxt!!, Main2Activity::class.java)) }
    }
}
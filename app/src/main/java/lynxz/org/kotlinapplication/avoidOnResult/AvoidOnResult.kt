package lynxz.org.kotlinapplication.avoidOnResult

import android.app.Activity
import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity

/**
 * Created by lynxz on 02/01/2018.
 *  https://juejin.im/post/5a4611786fb9a0451a76b565
 */
class AvoidOnResult(activity: FragmentActivity) {

    constructor(fragment: Fragment) : this(fragment.activity)

    private val TAG = "AvoidOnResult"
    private lateinit var avoidOnResultFragment: AvoidOnResultFragment

    init {
        avoidOnResultFragment = getAvoidOnResultFragment(activity)
    }

    private fun getAvoidOnResultFragment(activity: FragmentActivity): AvoidOnResultFragment {
        var avoidOnResultFragment: AvoidOnResultFragment? = findAvoidOnResultFragment(activity)
        if (avoidOnResultFragment == null) {
            avoidOnResultFragment = AvoidOnResultFragment()
            activity.supportFragmentManager.apply {
                beginTransaction()
                        .add(avoidOnResultFragment, TAG)
                        .commitAllowingStateLoss()
                executePendingTransactions()
            }
        }
        return avoidOnResultFragment
    }

    private fun findAvoidOnResultFragment(activity: Activity): AvoidOnResultFragment? {
        return activity.fragmentManager.findFragmentByTag(TAG) as AvoidOnResultFragment?
    }

    fun startForResult(intent: Intent, requestCode: Int, callback: Callback? = null) = avoidOnResultFragment.startForResult(intent, requestCode, callback)
    fun startForResult(clazz: Class<*>, requestCode: Int, callback: Callback? = null) = avoidOnResultFragment.startForResult(Intent(avoidOnResultFragment.activity, clazz), requestCode, callback)

    interface Callback {
        fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    }
}
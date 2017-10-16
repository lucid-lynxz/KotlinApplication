package lynxz.org.kotlinapplication.activity

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_animation.*
import lynxz.org.kotlinapplication.R

/**
 * Created by lynxz on 25/09/2017.
 */
class AnimationActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation)

        tv_translate.setOnClickListener { scaleAnimate() }
        tv_rotate.setOnClickListener { rotateAnimate() }
        tv_value_animator.setOnClickListener { valueAnimate() }
    }

    private fun valueAnimate() {
        ValueAnimator.ofInt(0, 100).apply {
            duration = 1000
            addUpdateListener { valueAnimator ->
                val currentInt: Int = valueAnimator.animatedValue as Int
                val currentFraction = valueAnimator.animatedFraction
                tv_target.text = "$currentInt"
//                Logger.d("currentValue is $currentInt")
            }
            start()
        }
    }

    private fun rotateAnimate() {
        ObjectAnimator.ofFloat(tv_target, "rotation", 0f, 360f).apply {
            duration = 1000
            repeatMode = ObjectAnimator.REVERSE
            repeatCount = 1

            start()
        }
    }

    private fun scaleAnimate() {
        ObjectAnimator.ofFloat(tv_target, "translationX", 200f).apply {
            duration = 1000
//            repeatMode = ObjectAnimator.REVERSE
//            repeatCount = 1

            start()
        }
    }

}
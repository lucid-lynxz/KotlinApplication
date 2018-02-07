package lynxz.org.kotlinapplication.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import lynxz.org.kotlinapplication.R
import lynxz.org.kotlinapplication.util.Logger
import kotlin.math.min

/**
 * Created by lynxz on 29/01/2018.
 * 博客: https://juejin.im/user/5812c2b0570c3500605a15ff
 */
class ArcProgress(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private var foreColor = Color.GREEN // 进度前景色
    private var bgColor = Color.LTGRAY //背景色
    private var pointColor = Color.DKGRAY
    private var startAngle = -140f // 圆弧起始角度
    private var sweepAngle = 100f // 圆弧扫过的角度
    private var barWidth = 8f
    private var currentProgress = 0f // 当前已完成的进度 0-100
    private var pointRadius = 4f
    private var pointCount = 3

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.ArcProgress, 0, 0).let {
            foreColor = it.getColor(R.styleable.ArcProgress_progress_color, Color.GREEN)
            bgColor = it.getColor(R.styleable.ArcProgress_background_color, Color.LTGRAY)
            pointColor = it.getColor(R.styleable.ArcProgress_point_color, Color.DKGRAY)
            startAngle = it.getFloat(R.styleable.ArcProgress_start_angle, -140f)
            sweepAngle = it.getFloat(R.styleable.ArcProgress_sweep_angle, 100f)
            barWidth = it.getDimension(R.styleable.ArcProgress_bar_width, 8f)
            currentProgress = it.getFloat(R.styleable.ArcProgress_progress, 0f)
            pointCount = it.getInt(R.styleable.ArcProgress_progress_count, 3)
            pointRadius = barWidth / 3
//            Logger.d("barWidth = $barWidth")
            it.recycle()
        }
    }

    private val mBgPaint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            color = bgColor
            strokeWidth = barWidth
        }
    }


    private val mForePaint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            color = foreColor
            strokeWidth = barWidth
        }
    }

    private val mPointPaint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            color = pointColor
            strokeWidth = pointRadius
        }
    }


    private var ovalRect = RectF(0f, 0f, 600f, 600f)
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
////        ovalRect = RectF(left.toFloat(), top.toFloat() + barWidth / 2,
////                right.toFloat(), bottom.toFloat() - barWidth / 2)
////        Logger.d(ovalRect.toShortString())
        val rectLeft = barWidth / 2
        val rectTop = barWidth / 2
        val ovalWidth = min(width, height)

        ovalRect = RectF(left.toFloat(), top.toFloat() + barWidth / 2,
                rectLeft + ovalWidth, rectTop + ovalWidth - barWidth / 2)
    }

    private fun getRect() {
        val rectLeft = barWidth / 2
        val rectTop = barWidth / 2
        val ovalWidth = min(width, height)

        ovalRect = RectF(left.toFloat(), top.toFloat() + barWidth / 2,
                rectLeft + ovalWidth, rectTop + ovalWidth - barWidth / 2)
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        getRect()
        canvas?.apply {
            //            drawRect(ovalRect, mForePaint)
            Logger.d("startAngle = $startAngle ,sweepAngle = $sweepAngle")
            drawArc(ovalRect, startAngle, sweepAngle, true, mBgPaint)
            drawRect(ovalRect, mBgPaint)
//            drawArc(ovalRect, -140f, 100f, true, mBgPaint)
            if (currentProgress in 0..100) {
                val sweepProgressAngle = sweepAngle / 100 * currentProgress
//                drawArc(ovalRect, startAngle, sweepProgressAngle, true, mForePaint)
            }

            if (pointCount in 2..99) {
                val a = ovalRect.width() / 2 - barWidth + pointRadius
                val b = ovalRect.height() / 2 - barWidth + pointRadius
                val perAngle = sweepAngle / pointCount

                for (i in 0..pointCount) {
                    val angle = (startAngle + perAngle * i) % 360
                    val angleR = angle * Math.PI / 180

                    val tan = Math.tan(angleR)
                    var x = Math.sqrt(a * a * b * b / (b * b + a * a * tan * tan))
                    var y = tan * x
                    if (angle <= -90) {
                        x = -x
                        y = -y
                    }

                    val actX = x + ovalRect.width() / 2 + ovalRect.left
                    val actY = y + ovalRect.height() / 2 + ovalRect.top

                    // 1440 1027
                    Logger.d(" $angle $tan $x ... $y  actY = $actY")
                    drawCircle(actX.toFloat(), actY.toFloat(), pointRadius, mPointPaint)
                }
            }
        }
    }
}
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
    private var barWidth = 10f
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
            barWidth = it.getDimension(R.styleable.ArcProgress_bar_width, 10f)
            currentProgress = it.getFloat(R.styleable.ArcProgress_progress, 0f)
            pointCount = it.getInt(R.styleable.ArcProgress_progress_count, 3)
            pointRadius = barWidth / 3
            Logger.d("barWidth = $barWidth")
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


    private lateinit var ovalRect: RectF

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        ovalRect = RectF(left.toFloat(), top.toFloat() + barWidth / 2,
                right.toFloat(), bottom.toFloat() - barWidth / 2)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.apply {
            drawRect(ovalRect, mForePaint)
            drawArc(ovalRect, startAngle, sweepAngle, false, mBgPaint)
            if (currentProgress in 0..100) {
                val sweepProgressAngle = sweepAngle / 100 * currentProgress
                drawArc(ovalRect, startAngle, sweepProgressAngle, false, mForePaint)
            }

//            if (pointCount in 2..99) {
//
//                val a = ovalRect.width() / 2 - barWidth / 2 + pointRadius
//                val b = ovalRect.height() / 2 - barWidth / 2 + pointRadius
//                val perAngle = sweepAngle / pointCount
//
//                for (i in 1..pointCount) {
//                    val angle = -1 * (startAngle + perAngle * i) % 360
//
//                    val angleR = angle * Math.PI / 180
//
//                    val x = Math.sqrt(a * a * b * b / (b * b + a * a * Math.tan(angleR)))
//                    val y = Math.tan(angleR) * x
//
//                    val actX = x + a
//                    val actY = Math.abs(b - y)
//
//                    // 1440 1027
//                    Logger.d(" $angle ${Math.tan(angleR)} $actX ... $b $y  actY = $actY")
//                    drawCircle(actX.toFloat(), actY.toFloat(), pointRadius, mForePaint)
////                    drawCircle(400f, 400f, pointRadius, mForePaint)
////                    drawCircle(922f, 400f, pointRadius, mForePaint)
////                    drawCircle(1057f, 400f, pointRadius, mForePaint)
////                    drawCircle(1156f, 400f, pointRadius, mForePaint)
//                }
//            }
        }
    }
}
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
 *
 * 用于绘制带刻度的进度条圆弧(上半圆弧)
 */
class ArcProgress(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private var foreColor = Color.GREEN // 进度前景色
    private var bgColor = Color.LTGRAY //背景色
    private var pointColor = Color.DKGRAY
    private var startAngle = -0f // 圆弧起始角度
    private var sweepAngle = 0f // 圆弧扫过的角度
    private var barWidth = 0f
    private var currentProgress = 0 // 当前已完成的进度 0-pointCount
    private var pointCount = 0 // 总进度等分数
    private var pointRadius = 0f
    private var ovalRect = RectF(0f, 0f, 600f, 600f)

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.ArcProgress, 0, 0).let {
            foreColor = it.getColor(R.styleable.ArcProgress_progress_color, Color.GREEN)
            bgColor = it.getColor(R.styleable.ArcProgress_background_color, Color.LTGRAY)
            pointColor = it.getColor(R.styleable.ArcProgress_point_color, Color.DKGRAY)
            startAngle = it.getFloat(R.styleable.ArcProgress_start_angle, -140f)
            sweepAngle = it.getFloat(R.styleable.ArcProgress_sweep_angle, 100f)
            barWidth = it.getDimension(R.styleable.ArcProgress_bar_width, 10f)
            currentProgress = it.getInt(R.styleable.ArcProgress_progress, 0)
            pointCount = it.getInt(R.styleable.ArcProgress_progress_count, 0)
            pointRadius = barWidth / 4

            pointCount = if (pointCount >= 100) 100 else pointCount
            it.recycle()
        }
    }

    /**
     * 设置进度
     * */
    fun setProgress(newProgress: Int) {
        currentProgress = newProgress
        invalidate()
    }

    /**
     * 获取当前进度
     * */
    fun getProgress(): Int {
        return currentProgress
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
            style = Paint.Style.FILL_AND_STROKE
            color = pointColor
            strokeWidth = pointRadius
        }
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        // 若定义的 RectF 不是正方形, 则 canvas.drawArc(...) 时, 绘制出来的 startAngle 与传入值不匹配, 会有偏差, 原因不明
//        ovalRect = RectF(left.toFloat(), top.toFloat() + barWidth / 2,
//                right.toFloat(), bottom.toFloat() - barWidth / 2)
//        Logger.d(ovalRect.toShortString())

        val rectLeft = barWidth / 2
        val rectTop = barWidth / 2
        val ovalWidth = min(width, height)

        ovalRect = RectF(left.toFloat(), top.toFloat() + barWidth / 2,
                rectLeft + ovalWidth, rectTop + ovalWidth - barWidth / 2)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (pointCount in 2..currentProgress) {
            currentProgress = pointCount
        } else if (currentProgress < 0) {
            currentProgress = 0
        }

        canvas?.apply {
            Logger.d("startAngle = $startAngle ,sweepAngle = $sweepAngle")
            drawArc(ovalRect, startAngle, sweepAngle, false, mBgPaint)

            var perAngle = sweepAngle / 100

            if (pointCount in 2..99) {
                perAngle = sweepAngle / pointCount
            }

            if (currentProgress in 0..100) {
                var sweepProgressAngle = perAngle * currentProgress
                if (sweepProgressAngle > sweepAngle) {
                    sweepProgressAngle = sweepAngle
                }
                drawArc(ovalRect, startAngle, sweepProgressAngle, false, mForePaint)
            }

            if (pointCount in 2..99) {
                val a = ovalRect.width() / 2 - barWidth + pointRadius
                val b = ovalRect.height() / 2 - barWidth + pointRadius


                for (i in 1 until pointCount) {
                    val angle = (startAngle + perAngle * i) % 360
                    val angleR = angle * Math.PI / 180

                    val tan = Math.tan(angleR)
                    var x = Math.sqrt(a * a * b * b / (b * b + a * a * tan * tan))
                    var y = tan * x
                    if (angle <= -90 || angle in 180..270) {
                        x = -x
                        y = -y
                    }

                    val actX = x + ovalRect.width() / 2 + ovalRect.left
                    val actY = y + ovalRect.height() / 2 + ovalRect.top - pointRadius * 2

                    drawCircle(actX.toFloat(), actY.toFloat(), pointRadius, mPointPaint)
                }
            }
        }
    }
}
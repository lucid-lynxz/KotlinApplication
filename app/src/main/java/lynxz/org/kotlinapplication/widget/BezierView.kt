package lynxz.org.kotlinapplication.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

/**
 * Created by lynxz on 16/10/2017.
 * 基础贝塞尔曲线
 */
class BezierView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private var startPoint = Pair(0, 0)
    private var endPoint = Pair(0, 0)
    private var controlPoint = Pair(0, 0)

    private var mPath: Path? = null
    private val mPaint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            color = Color.RED
            strokeWidth = 8f
        }
    }

    private val mInfoPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            color = Color.GRAY
            strokeWidth = 3f
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        startPoint = Pair(w / 2, h / 2)
        endPoint = Pair(w * 3 / 4, h / 2)
        controlPoint = Pair(w / 2, h / 2 - 300)
        mPath = Path()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        mPath?.let {
            it.reset()
            it.moveTo(startPoint.first.toFloat(), startPoint.second.toFloat())
            it.quadTo(controlPoint.first.toFloat(), controlPoint.second.toFloat(), endPoint.first.toFloat(), endPoint.second.toFloat())
            canvas?.drawPath(mPath, mPaint)
        }

        canvas?.drawLine(startPoint.first.toFloat(), startPoint.second.toFloat(), controlPoint.first.toFloat(), controlPoint.second.toFloat(), mInfoPaint)
        canvas?.drawLine(controlPoint.first.toFloat(), controlPoint.second.toFloat(), endPoint.first.toFloat(), endPoint.second.toFloat(), mInfoPaint)

        canvas?.drawText("start", startPoint.first.toFloat() - 30, startPoint.second.toFloat(), mInfoPaint)
        canvas?.drawText("control", controlPoint.first.toFloat() - 30, controlPoint.second.toFloat(), mInfoPaint)
        canvas?.drawText("end", endPoint.first.toFloat() - 30, endPoint.second.toFloat(), mInfoPaint)
    }
}
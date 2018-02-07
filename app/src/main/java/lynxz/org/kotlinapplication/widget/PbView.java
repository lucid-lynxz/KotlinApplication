package lynxz.org.kotlinapplication.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import lynxz.org.kotlinapplication.util.Logger;

/**
 * Created by Administrator on 2018/2/6.
 */

public class PbView extends View {
    private String TAG = PbView.this.getClass().getSimpleName();
    private int bgColor = Color.RED;
    private int pb = 0;
    private float mLineWith = 4.0f;

    public PbView(Context context) {
        super(context);
    }

    public PbView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PbView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PbView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBg(canvas);
    }

    private void drawBg(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(mLineWith);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        //规定居中圆弧的矩形边界
        float ltx = getWidth() / 2 - getHeight() / 4;//矩形左边的X坐标，
        float lty = getHeight() / 2 - getHeight() / 4;//矩形上边点的Y坐标
        float rbx = getWidth() / 2 + getHeight() / 4;//矩形右边X坐标
        float rby = getHeight() / 2 + getHeight() / 4;//矩形下边Y坐标
        //左X，上Y，又X，下Y，一个矩形的左上角坐标与又下角坐标
        RectF oval = new RectF(ltx, lty, rbx, rby);
        Logger.d(oval.toString(), "xxx");
        Logger.d(oval.width() + "  " + oval.height(), "xxx");

//        oval = new RectF(0, 0, 1200, 600);
        canvas.drawArc(oval, -140, 100, true, paint);
    }
}

package com.youngtr.library;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2017/9/12.
 */

public class BezierSimpleView extends View {

    private Paint mCirclePaint;
    private Paint mLinePaint;
    private Path mPath = new Path();

    private PointF mStartPoint = new PointF();
    private PointF mEndPoint = new PointF();
    private PointF mQuadPoint = new PointF();

    public BezierSimpleView(Context context) {
        this(context, null);
    }

    public BezierSimpleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BezierSimpleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mCirclePaint = new Paint();
        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(Color.RED);

        mLinePaint = new Paint();
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(1.5f);
        mLinePaint.setAntiAlias(true);
        mLinePaint.setColor(Color.BLACK);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mStartPoint.x = getPaddingLeft() + getWidth() / 4;
        mStartPoint.y = getHeight() / 2;

        mEndPoint.x = getWidth() - getPaddingRight() - getWidth() / 4;
        mEndPoint.y = getHeight() / 2;

        mQuadPoint.x = getWidth() / 2;
        mQuadPoint.y = getHeight() / 2 - 200;


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                mQuadPoint.x = (int) event.getX();
                mQuadPoint.y = (int) event.getY();
                invalidate();
                break;
        }
        return true;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        //draw circle
        canvas.drawCircle(mStartPoint.x, mStartPoint.y, 10, mCirclePaint);
        canvas.drawCircle(mEndPoint.x, mEndPoint.y, 10, mCirclePaint);
        canvas.drawCircle(mQuadPoint.x, mQuadPoint.y, 10, mCirclePaint);
        //draw line
        canvas.drawLine(mStartPoint.x, mStartPoint.y, mQuadPoint.x, mQuadPoint.y, mLinePaint);
        canvas.drawLine(mQuadPoint.x, mQuadPoint.y, mEndPoint.x, mEndPoint.y, mLinePaint);
        //draw bezier
        mPath.moveTo(mStartPoint.x, mStartPoint.y);
        mPath.quadTo(mQuadPoint.x, mQuadPoint.y, mEndPoint.x, mEndPoint.y);
        canvas.drawPath(mPath, mLinePaint);
    }
}

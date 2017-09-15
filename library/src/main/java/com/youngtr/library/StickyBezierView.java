package com.youngtr.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.youngtr.library.Utils.GeometryUtil;

/**
 * Created by YoungTr on 2017/9/14.
 * StickyBezierView
 */

public class StickyBezierView extends View {

    private static final float DEFAULT_FIX_CIRCLE_RADIUS = 15.0F;
    private static final float DEFAULT_DRAG_CIRCLE_RADIUS = 20.0F;
    private static final float DEFAULT_CIRCLE_OFFSET = 75.0F;

    private static final int DEFAULT_COLOR = Color.RED;

    private PointF mFixCircleCenter = new PointF();
    private PointF mDragCircleCenter = new PointF();
    private PointF mControlPoint = new PointF();

    private PointF[] mFixCutPoints = new PointF[2];
    private PointF[] mDragCutPoints;

    private Paint mPaint;
    private Path mPath;

    private float mFixCircleRadius = DEFAULT_FIX_CIRCLE_RADIUS;
    private float mDragCircleRadius = DEFAULT_DRAG_CIRCLE_RADIUS;
    private float mCircleOffset = DEFAULT_CIRCLE_OFFSET;

    private int mColor;

    public StickyBezierView(Context context) {
        this(context, null);
    }

    public StickyBezierView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StickyBezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
        init();
    }

    private void initAttrs(AttributeSet attrs) {
        final TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.StickyBezierViewStyle);
        mColor = attributes.getColor(R.styleable.StickyBezierViewStyle_color, DEFAULT_COLOR);
        mFixCircleRadius = attributes.getDimension(R.styleable.StickyBezierViewStyle_fixCircleRadius, DEFAULT_FIX_CIRCLE_RADIUS);
        mDragCircleRadius = attributes.getDimension(R.styleable.StickyBezierViewStyle_dragCircleRadius, DEFAULT_DRAG_CIRCLE_RADIUS);
        mCircleOffset = attributes.getDimension(R.styleable.StickyBezierViewStyle_circleOffset, DEFAULT_CIRCLE_OFFSET);
        attributes.recycle();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setColor(mColor);

        mPath = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mFixCircleCenter.x = getWidth() / 2;
        mFixCircleCenter.y = getHeight() / 2 - mCircleOffset;

        mDragCircleCenter.x = getWidth() / 2;
        mDragCircleCenter.y = getHeight() / 2 + mCircleOffset;
        mControlPoint = GeometryUtil.getMiddlePoint(mFixCircleCenter, mDragCircleCenter);
        mFixCutPoints = GeometryUtil.getCutPointF(mFixCircleCenter, mControlPoint, mFixCircleRadius);
        mDragCutPoints = GeometryUtil.getCutPointF(mDragCircleCenter, mControlPoint, mDragCircleRadius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        //draw circle
        canvas.drawCircle(mFixCircleCenter.x, mFixCircleCenter.y, mFixCircleRadius, mPaint);
        canvas.drawCircle(mDragCircleCenter.x, mDragCircleCenter.y, mDragCircleRadius, mPaint);
        //draw path;
        mPath.moveTo(mFixCutPoints[0].x, mFixCutPoints[0].y);
        mPath.lineTo(mFixCutPoints[1].x, mFixCutPoints[1].y);
        mPath.quadTo(mControlPoint.x, mControlPoint.y, mDragCutPoints[1].x, mDragCutPoints[1].y);
        mPath.lineTo(mDragCutPoints[0].x, mDragCutPoints[0].y);
        mPath.quadTo(mControlPoint.x, mControlPoint.y, mFixCutPoints[0].x, mFixCutPoints[0].y);
        mPath.close();
        canvas.drawPath(mPath, mPaint);
    }
}

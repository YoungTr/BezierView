package com.youngtr.library;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by YoungTr on 2017/9/12.
 * BezierWaveView
 */

public class BezierWaveView extends View implements View.OnClickListener {

    public static final int DEFAULT_WAVE_LENGTH = 1000;
    public static final int DEFAULT_WAVE_Y_OFFSET = 60;
    private static final String GRADIENT_START_COLOR = "#0000F5FF";
    private static final String GRADIENT_END_COLOR = "#BF00F5FF";

    private Paint mWavePaint;
    private Path mPath;

    private int mWidth;
    private int mHeight;
    private int mWaveCount;
    private int mWaveLength = DEFAULT_WAVE_LENGTH;
    private int mCenterY = DEFAULT_WAVE_Y_OFFSET;
    private int mXOffset;
    private int mYOffset = DEFAULT_WAVE_Y_OFFSET;

    private float mRate;


    public BezierWaveView(Context context) {
        this(context, null);
    }

    public BezierWaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BezierWaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mWavePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mWavePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mWavePaint.setColor(Color.LTGRAY);
        mPath = new Path();

        setOnClickListener(this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        mWaveCount = (int) Math.round(mWidth / mWaveLength + 1.5);
        mCenterY = mHeight - DEFAULT_WAVE_Y_OFFSET;
        LinearGradient lg = new LinearGradient(0, 0, 0, mHeight, Color.parseColor(GRADIENT_START_COLOR), Color.parseColor(GRADIENT_END_COLOR), Shader.TileMode.CLAMP);// CLAMP重复最后一个颜色至最后
        mWavePaint.setShader(lg);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        mPath.moveTo(-mWaveLength + mXOffset, mCenterY);
        for (int i = 0; i < mWaveCount; i++) {
            mPath.quadTo((-mWaveLength * 3 / 4) + (i * mWaveLength) + mXOffset, mCenterY + mYOffset, (-mWaveLength / 2) + (i * mWaveLength) + mXOffset, mCenterY);
            mPath.quadTo((-mWaveLength / 4) + (i * mWaveLength) + mXOffset, mCenterY - mYOffset, i * mWaveLength + mXOffset, mCenterY);
        }
        mPath.lineTo(mWidth, mHeight);
        mPath.lineTo(0, mHeight);
        mPath.close();
        canvas.drawPath(mPath, mWavePaint);
    }

    @Override
    public void onClick(View view) {
        ValueAnimator animator = ValueAnimator.ofInt(0, mWaveLength);
        animator.setDuration(1000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.d("BezierWaveView", "animation.getAnimatedValue():" + animation.getAnimatedValue());

                mXOffset = (int) animation.getAnimatedValue();
                Log.d("BezierWaveView", "(int) animation.getAnimatedValue() / mWaveLength:" + ((int) animation.getAnimatedValue() / mWaveLength));

                mRate += 0.002f;
                if (mRate >= 0.8f) {
                    mRate = 0.8f;
                }
                mCenterY = (int) ((mHeight - DEFAULT_WAVE_Y_OFFSET) * (1 - mRate) * 1.0f);
                invalidate();
            }
        });
        animator.start();
    }
}

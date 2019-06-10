package cn.app.cliplinearlayout.utils;

import android.graphics.Paint;

/**
 * Created Date 2019/4/25.
 *
 * @author RayChen
 * ClassDescription：画笔工具类
 */
public class PaintUtil {
    Paint mPaint;

    /**
     * 绘制特殊图形场景，默认抗锯齿
     *
     * @param strokeWidth
     * @param color
     * @param style
     * @return
     */
    public Paint getPaint(int strokeWidth, int color, Paint.Style style) {
        if (mPaint == null) {
            mPaint = new Paint();
        }
        mPaint.setAntiAlias(true);
        if (strokeWidth > 0) {
            mPaint.setStrokeWidth(strokeWidth);
        }
        mPaint.setColor(color);
        if (style != null) {
            mPaint.setStyle(style);
        }
        return mPaint;
    }

    /**
     * 绘制文本场景，默认黑色
     *
     * @param textSize
     * @param antiAlias
     * @return
     */
    public Paint getPaint(int textSize, boolean antiAlias) {
        if (mPaint == null) {
            mPaint = new Paint();
        }
        mPaint.setAntiAlias(antiAlias);
        if (textSize > 0) {
            mPaint.setTextSize(textSize);
        }
        return mPaint;
    }

    /**
     * 绘制文本场景
     *
     * @param textSize  文字大小
     * @param antiAlias 是否抗锯齿
     * @param color     颜色值
     * @return
     */
    public Paint getPaint(int textSize, boolean antiAlias, int color) {
        if (mPaint == null) {
            mPaint = new Paint();
        }
        mPaint.setAntiAlias(antiAlias);
        if (textSize > 0) {
            mPaint.setTextSize(textSize);
        }

        mPaint.setColor(color);
        return mPaint;
    }

    /**
     * 获取画笔，属性比较齐全，可不传
     *
     * @param textSize
     * @param antiAlias
     * @param strokeWidth
     * @param color
     * @param style
     * @param alpha
     * @return
     */
    public Paint getPaint(int textSize, boolean antiAlias, int strokeWidth, int color, Paint.Style style, int alpha) {
        if (mPaint == null) {
            mPaint = new Paint();
        }
        if (textSize > 0) {
            mPaint.setTextSize(textSize);
        }
        mPaint.setAntiAlias(antiAlias);
        if (strokeWidth > 0) {
            mPaint.setStrokeWidth(strokeWidth);
        }
        mPaint.setColor(color);
        if (style != null) {
            mPaint.setStyle(style);
        }
        if (alpha >= 0 && alpha <= 1) {
            mPaint.setAlpha(alpha);
        } else {
            throw new RuntimeException("Please set a valid alpha value 0 ~ 1");
        }
        return mPaint;
    }

    public void setPaint(Paint paint) {
        mPaint = paint;
    }

    private static PaintUtil instance = null;

    private PaintUtil() {
    }

    public static PaintUtil getInstance() {
        synchronized (PaintUtil.class) {
            if (instance == null) {
                instance = new PaintUtil();
            }
        }
        return instance;
    }

}

package cn.app.cll.interfaces;

import android.graphics.Canvas;

/**
 * Created Date 2019/5/31.
 *
 * @author RayChen
 * ClassDescription：View绘制监听
 */
public interface OnViewDrawListener {
    /**
     * itemView绘制结束的监听
     * @param canvas 绘制的canvas
     * @param x 裁剪的圆心x坐标
     * @param y 裁剪的圆心y坐标
     * @param radius 裁剪圆实际半径
     */
    void onViewDrawEndListener(Canvas canvas,float x,float y,float radius);
}

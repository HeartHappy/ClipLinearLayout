package cn.app.cliplinearlayout.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.widget.LinearLayout;
import cn.app.cliplinearlayout.R;
import cn.app.cliplinearlayout.utils.PaintUtil;


/**
 * Created Date 2019/6/9.
 *
 * @author RayChen
 * ClassDescription：自定义裁剪LinearLayout
 */
public class ClipLinearLayout extends LinearLayout {


    //不可操作属性
    private int mWidth;//控件宽
    private int mHeight;//控件高
    private RectF mParentRect;//父控件Rect
    private float mX;//圆心x，根据子View位置计算X坐标
    private float mY;//圆心y，同上
    private SparseArray<RectF> mSparseArray = new SparseArray<>();


    //可操作属性
    private int mClipBackgroundColor = Color.WHITE;
    private float mRadius;//圆的半径


    @Override
    public void setBackgroundColor(int backgroundColor) {
        this.mClipBackgroundColor = backgroundColor;
        invalidate();
    }


    private void setCirCleCoordinate(float x, float y, float radius) {
        mX = x;
        mY = y;
        mRadius = radius;
        invalidate();
    }


    public ClipLinearLayout(Context context) {
        this(context, null);
    }

    public ClipLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("ResourceType")
    public ClipLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mParentRect = new RectF();
        initAttrs(context, attrs);


    }

    private void initAttrs(Context context, AttributeSet attrs) {
        @SuppressLint("CustomViewStyleable") TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.clip_layout);
        if (typedArray != null) {
            Drawable drawable = typedArray.getDrawable(R.styleable.clip_layout_clip_background);
            if (drawable != null) {
                if (drawable instanceof ColorDrawable) {
                    mClipBackgroundColor = ((ColorDrawable) drawable).getColor();
                } else {
                    throw new RuntimeException("Please set color value,Temporarily does not support the Drawable");
                }
            }
            typedArray.recycle();
        }
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
        mHeight = h;
        super.onSizeChanged(w, h, oldw, oldh);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
            addChildLayout(i, getChildAt(i).getId());
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mParentRect.set(l, t, r, b);
        super.onLayout(changed, l, t, r, b);
    }


    /**
     * 自定义ViewGroup没有设置background，不会执行
     *
     * @param canvas canvas
     */
    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        drawCanvas(canvas);
    }


    /**
     * 自定义ViewGroup没有设置background会执行
     *
     * @param canvas canvas
     */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        drawCanvas(canvas);
        super.dispatchDraw(canvas);
    }


    /**
     * ViewGroup重绘
     *
     * @param canvas canvas
     */
    private void drawCanvas(Canvas canvas) {
        Path circlePath = new Path();
        circlePath.addCircle(mX, mY, mRadius, Path.Direction.CW);
        Path roundRectPath = new Path();
        roundRectPath.addRoundRect(new RectF(0, 0, mWidth, mHeight), (float) mWidth / 2, (float) mWidth / 2, Path.Direction.CCW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            roundRectPath.op(circlePath, Path.Op.DIFFERENCE);
        }
        canvas.drawPath(roundRectPath, PaintUtil.getInstance().getPaint(0, mClipBackgroundColor, Paint.Style.FILL_AND_STROKE));
    }


    /**
     * 添加孩子的layout参数
     *
     * @param i  孩子下标
     * @param id 需要设置裁剪View的id
     */
    private void addChildLayout(final int i, final int id) {
        getChildAt(i).addOnLayoutChangeListener(new OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (mSparseArray.get(id) == null) {
                    mSparseArray.put(id, new RectF(left, top, right, bottom));
                }
            }
        });
    }


    /**
     * 设置裁剪位置根据子ChildView的id
     *
     * @param id     childView的id 必须设置id属性
     * @param radius 裁剪圆的半径
     */
    public void setDropCirCleByViewId(int id, int radius) {
        if (mSparseArray.get(id) != null) {
            RectF rectF = mSparseArray.get(id);
            this.setCirCleCoordinate((rectF.right - rectF.left) / 2 + rectF.left, (rectF.bottom - rectF.top) / 2 + rectF.top, radius);
        }
    }
}

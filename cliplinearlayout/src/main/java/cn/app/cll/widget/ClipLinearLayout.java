package cn.app.cll.widget;

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

import cn.app.cll.R;
import cn.app.cll.builder.ClipLayoutAttrs;
import cn.app.cll.interfaces.OnClickClipListener;


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
    private Paint mPaint;//画笔
    private float mRadius;//圆的半径
    private SparseArray<RectF> mSparseArray = new SparseArray<>();
    private View mPreView;//上一个选中View
    private OnClickClipListener mOnClickClipListener;


    //可操作属性
    private int mRoundBackgroundColor = Color.WHITE;
    private boolean mSupportAnim;//是否支持动画
    private float mScaleX;//缩放X
    private float mScaleY;//缩放Y
    private long mDuration;//动画时长
    private float mClipSize;//裁剪大小，单位像素px


    public void setOnClickClipListener(OnClickClipListener onClickClipListener) {
        mOnClickClipListener = onClickClipListener;
    }


    public void builder(ClipLayoutAttrs attrs) {
        mSupportAnim = attrs.isSupportAnim();
        mRoundBackgroundColor = attrs.getRoundBackgroundColor();
        mScaleX = attrs.getScaleX();
        mScaleY = attrs.getScaleY();
        mDuration = attrs.getDuration();
        mClipSize = attrs.getClipSize();
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
        setClipChildren(false);
        setClipToPadding(false);
        mParentRect = new RectF();
        initAttrs(context, attrs);
        initPaint();
    }

    private void initPaint() {
        if (mPaint == null) {
            mPaint = new Paint();
        }
        mPaint.setAntiAlias(true);
        mPaint.setColor(mRoundBackgroundColor);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    /**
     * 从xml中获取属性值
     *
     * @param context 上下文
     * @param attrs   属性对象
     */
    private void initAttrs(Context context, AttributeSet attrs) {
        @SuppressLint("CustomViewStyleable") TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ClipLinearLayout);
        if (typedArray != null) {
            mRoundBackgroundColor = typedArray.getColor(R.styleable.ClipLinearLayout_clip_round_background, Color.WHITE);
            mSupportAnim = typedArray.getBoolean(R.styleable.ClipLinearLayout_clip_support_anim, false);
            mScaleX = typedArray.getFloat(R.styleable.ClipLinearLayout_clip_sel_scaleX, 1.0f);
            mScaleY = typedArray.getFloat(R.styleable.ClipLinearLayout_clip_sel_scaleY, 1.0f);
            mDuration = typedArray.getInteger(R.styleable.ClipLinearLayout_clip_anim_duration, 100);
            mClipSize = typedArray.getFloat(R.styleable.ClipLinearLayout_clip_size, 10);
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
        roundRectPath.addRoundRect(new RectF(getPaddingLeft(), getPaddingTop(), mWidth - getPaddingRight(), mHeight - getPaddingBottom()), (float) mWidth / 2, (float) mWidth / 2, Path.Direction.CCW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            roundRectPath.op(circlePath, Path.Op.DIFFERENCE);
        }
        canvas.drawPath(roundRectPath, mPaint);
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
     * 重绘背景色
     *
     * @param backgroundColor 背景色
     */
    @Override
    public void setBackgroundColor(int backgroundColor) {
        this.mRoundBackgroundColor = backgroundColor;
        initPaint();
        invalidate();
    }


    /**
     * 确定点进行重绘
     *
     * @param x      x
     * @param y      y
     * @param radius 实际半径
     */
    private void setCirCleCoordinate(float x, float y, float radius) {
        mX = x;
        mY = y;
        mRadius = radius;
        invalidate();
    }

    /**
     * 裁剪圆
     *
     * @param view   裁剪的View
     * @param radius 选中view的边缘到裁剪边缘的距离
     */
    private void clipCirCle(View view, float radius) {
        if (mSparseArray.get(view.getId()) != null) {
            RectF rectF = mSparseArray.get(view.getId());
            if (rectF == null) {
                throw new RuntimeException("Please set the view id");
            }
            this.setCirCleCoordinate((rectF.right - rectF.left) / 2 + rectF.left, (rectF.bottom - rectF.top) / 2 + rectF.top, view.getWidth() / 2 + radius);
        }
    }


    /**
     * 切换选中
     *
     * @param v view
     */
    private void switchToSel(View v) {
        v.setSelected(true);
        if (mOnClickClipListener != null) {
            mOnClickClipListener.onCurrentView(v);
        }
        if (mSupportAnim) {
            v.animate().scaleX(mScaleX).scaleY(mScaleY).setDuration(mDuration).start();
        }
    }


    /**
     * 切换默认
     */
    private void switchToDef() {
        if (mPreView != null) {
            mPreView.setSelected(false);
            if (mOnClickClipListener != null) {
                mOnClickClipListener.onPreviousView(mPreView);
            }
            if (mSupportAnim) {
                mPreView.animate().scaleX(1.0f).scaleY(1.0f).start();
            }
        }
    }


    /**
     * 裁剪选中的View
     *
     * @param view
     */
    public void clipSelectView(View view) {
        //切换默认
        switchToDef();
        //切换选中
        switchToSel(view);
        //裁剪具体操作
        clipCirCle(view, mClipSize);
        //记住上一个操作的View
        mPreView = view;
    }
}

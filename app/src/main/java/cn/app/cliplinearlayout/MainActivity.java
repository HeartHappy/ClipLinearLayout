package cn.app.cliplinearlayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;

import cn.app.cll.widget.ClipLinearLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ClipLinearLayout mClipLayout;
    private View mView;
    private View mIvThirtySecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViewAndListener();

        //View绘制完成监听，否则设置默认选中无效
        mClipLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                selectClipView(mIvThirtySecond);
            }
        });
    }


    private void initViewAndListener() {
        mClipLayout = findViewById(R.id.clipLayout);
        mIvThirtySecond = findViewById(R.id.ivThirtySecond);
        View ivSixtySeconds = findViewById(R.id.ivSixtySeconds);
        View ivTrisection = findViewById(R.id.ivTrisection);
        View ivFifth = findViewById(R.id.ivFifth);


        mIvThirtySecond.setOnClickListener(this);
        ivSixtySeconds.setOnClickListener(this);
        ivTrisection.setOnClickListener(this);
        ivFifth.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        selectClipView(v);
    }


    /**
     * 选中裁剪View
     *
     * @param v 需要裁剪的View
     */
    private void selectClipView(View v) {
        //切换默认
        switchToDef();
        //切换选中
        switchToSel(v);
        //裁剪具体操作
        mClipLayout.clipCirCle(v);
        //记住上一个操作的View
        mView = v;
    }

    /**
     * 切换选中
     *
     * @param v view
     */
    private void switchToSel(View v) {
        v.setSelected(true);
        v.animate().scaleX(1.87f).scaleY(1.87f).setDuration(100).start();
    }


    /**
     * 切换默认
     */
    private void switchToDef() {
        if (mView != null) {
            mView.setSelected(false);
            mView.animate().scaleX(1.0f).scaleY(1.0f).start();
        }
    }
}

package cn.app.cliplinearlayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;

import cn.app.cll.interfaces.OnClickClipListener;
import cn.app.cll.widget.ClipLinearLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ClipLinearLayout mClipLayout;
    private View mIvThirtySecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViewAndListener();

        //创建属性对象的构造器
       /* ClipLayoutAttrs.Builder builder = new ClipLayoutAttrs.Builder();

        //构造者模式创建属性对象
        ClipLayoutAttrs attrs =builder.roundBackgroundColor(Color.RED)
                        .supportAnim(true)
                        .scaleX(1.87f)
                        .scaleY(1.87f)
                        .duration(100)
                        .clipSize(45)
                        .build();

        //将需要的属性值设置到ClipLinearLayout中
        mClipLayout.builder(attrs);*/

        //View绘制完成监听，否则设置默认选中无效
        mClipLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mClipLayout.clipSelectView(mIvThirtySecond);
            }
        });

        //选中的监听
        mClipLayout.setOnClickClipListener(new OnClickClipListener() {
            @Override
            public void onPreviousView(View view) {
                //返回的上一个View
            }

            @Override
            public void onCurrentView(View view) {
                //返回当前View
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
        mClipLayout.clipSelectView(v);
    }


}

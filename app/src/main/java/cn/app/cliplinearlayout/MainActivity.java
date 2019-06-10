package cn.app.cliplinearlayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import cn.app.cliplinearlayout.widget.ClipLinearLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ClipLinearLayout mClipLayout;
    private View mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mClipLayout = findViewById(R.id.clipLayout);
        findViewById(R.id.iv30s).setOnClickListener(this);
        findViewById(R.id.iv60s).setOnClickListener(this);
        findViewById(R.id.iv3m).setOnClickListener(this);
        findViewById(R.id.iv5m).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //切换默认
        switchToDef();
        //切换选中
        switchToSel(v);
        //裁剪具体操作
        mClipLayout.setDropCirCleByViewId(v.getId(), v.getWidth() / 2 + 45);
        //记住上一个操作的View
        mView = v;
    }

    /**
     * 切换选中
     *
     * @param v view
     */
    private void switchToSel(View v) {
        switchToIcon(v, R.mipmap.icon_play_30s, R.mipmap.icon_play_60s, R.mipmap.icon_play_3m, R.mipmap.icon_play_5m);
        v.animate().scaleX(1.87f).scaleY(1.87f).setDuration(100).start();
    }


    /**
     * 切换默认
     */
    private void switchToDef() {
        if (mView != null) {
            switchToIcon(mView, R.mipmap.icon_play_30s_def, R.mipmap.icon_play_60s_def, R.mipmap.icon_play_3m_def, R.mipmap.icon_play_5m_def);
            mView.animate().scaleX(1.0f).scaleY(1.0f).start();
        }
    }


    /**
     * 具体切换
     *
     * @param v  view
     * @param p  图标1
     * @param p2 图标2
     * @param p3 图标3
     * @param p4 图标4
     */
    private void switchToIcon(View v, int p, int p2, int p3, int p4) {
        switch (v.getId()) {
            case R.id.iv30s:
                ((ImageView) v).setImageDrawable(getResources().getDrawable(p));
                break;
            case R.id.iv60s:
                ((ImageView) v).setImageDrawable(getResources().getDrawable(p2));
                break;
            case R.id.iv3m:
                ((ImageView) v).setImageDrawable(getResources().getDrawable(p3));
                break;
            case R.id.iv5m:
                ((ImageView) v).setImageDrawable(getResources().getDrawable(p4));
                break;
        }
    }
}

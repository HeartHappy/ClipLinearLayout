package cn.hearthappy.interfaces;

import android.view.View;

/**
 * Created Date 2019-07-19.
 *
 * @author RayChen
 * ClassDescription：点击选中监听
 */
public interface OnClickClipListener {


    //返回上一个View
    void onPreviousView(View view);
    //返回当前View
    void onCurrentView(View view);
}

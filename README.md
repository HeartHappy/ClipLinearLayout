# ClipLinearLayout
自定义控件之裁剪LinearLayout

### 示例:  
<img width="300"  src="https://github.com/HeartHappy/ClipLinearLayout/blob/master/cliplinearlayout/gifhome_1088x1920_5s.gif"/>
### 特性
- 特性：主要用来操作切换按钮时使用，选中的View周边裁剪后透露出底色，看到背景，给人视觉上一种新的感观

(说明项目的配置方法，android开源库多用Gradle导入)
### 配置


### 使用方法
| 属性名称 | 值/类型 | 简介 |
| :------: | :------: | :------: |
|clip_background | reference | 颜色值,默认白色 |

### java代码操作

```java 
//裁剪具体操作
ClipLinearLayout clipLayout = findViewById(R.id.clipLayout);
clipLayout.clipCirCle(v);

//默认裁剪，View绘制完成监听，否则设置默认选中无效
        mClipLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                selectClipView(mIvThirtySecond);
            }
        });

//设置父布局圆角矩形背景色	
setBackgroundColor（）

//方法参数说明
clipCirCle（裁剪子view，子view边缘到裁剪边缘的距离--像素px）
```


### 注意事项
子view必须设置id属性


# 欢迎使用ClipLinearLayout
裁剪LinearLayout

### 示例:  
<img width="300"  src="https://github.com/HeartHappy/ClipLinearLayout/blob/master/cliplinearlayout/gifhome_1088x1920_5s.gif"/>

### 特性
主要用来操作切换按钮时使用，选中的View周边裁剪后透露出底色，看到背景，给人视觉上一种新的感观

(说明项目的配置方法，android开源库多用Gradle导入)
### Gradle 引入
```java

implementation 'com.hearthappy:cliplinearlayout:1.0.4'

```

## 使用方法

### xml自定义属性

| 属性名称 |作用说明| 值/类型 | 默认值 |
| :------: | :------: | :------: | :--:|
|clip_round_background|圆角矩形背景色 | color | 默认白色 |
|clip_support_anim|是否支持放大动画 | boolean | 默认白色 |
|clip_sel_scaleX|X轴放大 | float | 默认1.0f（原始大小） |
|clip_sel_scaleY|Y轴放大 | float | 默认1.0f（原始大小） |
|clip_anim_duration|放大时长 | integer | 默认100 |
|clip_size|裁剪大小(单位像素) | float | 默认10px |

### java代码使用方式

```java 

ClipLinearLayout clipLayout = findViewById(R.id.clipLayout);
//java代码构建属性
//创建属性对象的构造器
ClipLayoutAttrs.Builder builder = new ClipLayoutAttrs.Builder();

//构造者模式创建属性对象
ClipLayoutAttrs attrs =builder.roundBackgroundColor(Color.RED)
                .supportAnim(true)
                .scaleX(1.87f)
                .scaleY(1.87f)
                .duration(100)
                .clipSize(45)
                .build();

//将需要的属性值设置到ClipLinearLayout中
clipLayout.builder(attrs);

```

```java 

//选中的View ，裁剪具体操作
clipLayout.clipSelectView(v);//裁剪的子view（注：这里具体裁剪的是ClipLinearLayout，根据传递的子view位置进行裁剪）

//选中的View ，不裁剪
clipLayout.clipSelectView(v,false);

//默认裁剪，View绘制完成监听，否则设置默认选中无效
clipLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
    @Override
    public void onGlobalLayout() {
        clipLayout.clipSelectView(childView);
    }
});

//设置父布局圆角矩形背景色	
clipLayout.setBackgroundColor(getResources().getColor(R.color.colorAccent));

//获取上一个选中View
clipLayout.getPreView();


```

```java 
//监听
clipLayout.setOnClickClipListener(new OnClickClipListener() {
    @Override
    public void onPreviousView(View view) {
        //返回的上一个View
    }

    @Override
    public void onCurrentView(View view) {
        //返回当前View
    }
});



```


### 注意事项
子view必须设置id属性

### 讨论交流方式
    邮箱:chenrui@etoc.cn
    QQ：1096885636

# ClipLinearLayout
自定义控件之裁剪LinearLayout

xml属性设置：
	clip_background 裁剪背景色
java代码设置：

//裁剪具体操作
ClipLinearLayout clipLayout = findViewById(R.id.clipLayout);
clipLayout.setDropCirCleByViewId(v.getId(), v.getWidth() / 2 + 45);

//方法参数说明
setDropCirCleByViewId（裁剪子View的id，裁剪子View的半径（一般为View的宽/2加上你想看到空缺的大小））


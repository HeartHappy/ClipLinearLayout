package cn.app.cll.builder;

import android.graphics.Color;

/**
 * Created Date 2019-07-19.
 *
 * @author RayChen
 * ClassDescription：裁剪的属性对象
 */
public class ClipLayoutAttrs {


    private int roundBackgroundColor;
    private boolean supportAnim;
    private float scaleX;
    private float scaleY;
    private long duration;
    private float clipSize;

    public int getRoundBackgroundColor() {
        return roundBackgroundColor;
    }

    public boolean isSupportAnim() {
        return supportAnim;
    }

    public float getScaleX() {
        return scaleX;
    }

    public float getScaleY() {
        return scaleY;
    }

    public long getDuration() {
        return duration;
    }

    public float getClipSize() {
        return clipSize;
    }

    private ClipLayoutAttrs(Builder builder) {
        roundBackgroundColor = builder.roundBackgroundColor;
        supportAnim = builder.supportAnim;
        scaleX = builder.scaleX;
        scaleY = builder.scaleY;
        duration = builder.duration;
        clipSize = builder.clipSize;
    }



    public static final class Builder {
        private int roundBackgroundColor = Color.WHITE;
        private boolean supportAnim;
        private float scaleX=1.0f;
        private float scaleY=1.0f;
        private long duration;
        private float clipSize=10;

        public Builder() {
        }

        public Builder roundBackgroundColor(int val) {
            roundBackgroundColor = val;
            return this;
        }

        public Builder supportAnim(boolean val) {
            supportAnim = val;
            return this;
        }

        public Builder scaleX(float val) {
            scaleX = val;
            return this;
        }

        public Builder scaleY(float val) {
            scaleY = val;
            return this;
        }

        public Builder duration(long val) {
            duration = val;
            return this;
        }

        public Builder clipSize(float val) {
            clipSize = val;
            return this;
        }

        public ClipLayoutAttrs build() {
            return new ClipLayoutAttrs(this);
        }
    }
}

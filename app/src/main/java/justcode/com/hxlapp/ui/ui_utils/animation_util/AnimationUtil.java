package justcode.com.hxlapp.ui.ui_utils.animation_util;


import android.content.Context;
import android.view.View;
import android.view.animation.AnimationSet;

public class AnimationUtil {
    Context context;
    View view;
    AbstractComponentAnimation componentAnimation;
    AnimationSet animationSet;
    public static final int ScaleBig = 0;
    public static final int ScaleSmall = 1;

    public static final int AlphaShow = 2;
    public static final int AlphaHide = 3;

    public static AnimationUtil newAnimation(Context context, View view) {
        return new AnimationUtil(context, view);
    }

    public AnimationUtil(Context context, View view) {
        this.context = context;
        this.view = view;
        componentAnimation = new ConponentAnimation();
        animationSet = new AnimationSet(true);
    }

    public AnimationUtil Scale(int scaleModel) {
        componentAnimation = new Scale(componentAnimation, scaleModel);
        return this;
    }

    public AnimationUtil Alpha(int scaleModel) {
        componentAnimation = new Alpha(componentAnimation, scaleModel);
        return this;
    }

    public void play() {
        componentAnimation.play(animationSet);
        view.startAnimation(animationSet);
    }
}

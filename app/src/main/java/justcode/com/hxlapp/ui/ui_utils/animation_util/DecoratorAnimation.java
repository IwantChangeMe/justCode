package justcode.com.hxlapp.ui.ui_utils.animation_util;

import android.util.Log;
import android.view.animation.AnimationSet;

/**
 * 这里准备用装饰器模式，实现动画的组合
 * 这个充当抽象装饰器
 */
public class DecoratorAnimation extends AbstractComponentAnimation {

    AbstractComponentAnimation componentAnimation;

    public DecoratorAnimation(AbstractComponentAnimation componentAnimation) {
        this.componentAnimation = componentAnimation;
        Log.d("AnimationObj:","DecoratorAnimation");
    }

    @Override
    protected void play(AnimationSet animationSet) {
        componentAnimation.play(animationSet);
        Log.d("AnimationObj:","DecoratorAnimation："+"play");
    }
}

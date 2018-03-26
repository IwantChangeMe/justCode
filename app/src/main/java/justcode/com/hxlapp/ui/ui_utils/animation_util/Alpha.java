package justcode.com.hxlapp.ui.ui_utils.animation_util;

import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;

/**
 * 透明度动画
 * 具体实现装饰器
 */
public class Alpha extends DecoratorAnimation {
    int scaleModel;

    public Alpha(AbstractComponentAnimation componentAnimation, int scaleModel) {
        super(componentAnimation);
        this.scaleModel = scaleModel;
        Log.d("AnimationObj:","Alpha");
    }

    @Override
    protected void play(AnimationSet animationSet) {
        super.play(animationSet);
        AlphaPlay(animationSet);
        Log.d("AnimationObj:","Alpha："+"play");
    }

    private void AlphaPlay(AnimationSet animationSet) {
        Animation alpha = null;
        switch (scaleModel) {
            case AnimationUtil.AlphaShow:
                alpha = new AlphaAnimation(0f,1f);
                alpha.setInterpolator(new AccelerateDecelerateInterpolator());
                break;
            case AnimationUtil.AlphaHide:
                alpha = new AlphaAnimation(1f,0f);
                alpha.setInterpolator(new AccelerateInterpolator());
                break;
        }
        /**
         //设置动画为加速动画(动画播放中越来越快)
         scaleAnimation.setInterpolator(new AccelerateInterpolator());
         //设置动画为减速动画(动画播放中越来越慢)
         scaleAnimation.setInterpolator(new DecelerateInterpolator());
         //设置动画为先加速在减速(开始速度最快 逐渐减慢)
         scaleAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
         //先反向执行一段，然后再加速反向回来（相当于我们弹簧，先反向压缩一小段，然后在加速弹出）
         scaleAnimation.setInterpolator(new AnticipateInterpolator());
         //同上先反向一段，然后加速反向回来，执行完毕自带回弹效果（更形象的弹簧效果）
         scaleAnimation.setInterpolator(new AnticipateOvershootInterpolator());
         //执行完毕之后会回弹跳跃几段（相当于我们高空掉下一颗皮球，到地面是会跳动几下）
         scaleAnimation.setInterpolator(new BounceInterpolator());
         //循环，动画循环一定次数，值的改变为一正弦函数：Math.sin(2* mCycles* Math.PI* input)
         scaleAnimation.setInterpolator(new CycleInterpolator(2));
         //线性均匀改变
         scaleAnimation.setInterpolator(new LinearInterpolator());
         //加速执行，结束之后回弹
         scaleAnimation.setInterpolator(new OvershootInterpolator());
         */

        alpha.setFillAfter(true);
        //1.5秒完成动画
        alpha.setDuration(500);
        //将AlphaAnimation这个已经设置好的动画添加到 AnimationSet中
        animationSet.addAnimation(alpha);

    }
}

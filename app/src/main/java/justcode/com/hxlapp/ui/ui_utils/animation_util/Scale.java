package justcode.com.hxlapp.ui.ui_utils.animation_util;

import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;

/**
 * 缩放动画
 * 具体实现装饰器
 */
public class Scale extends DecoratorAnimation {

    int scaleModel;

    public Scale(AbstractComponentAnimation componentAnimation, int scaleModel) {
        super(componentAnimation);
        this.scaleModel = scaleModel;
        Log.d("AnimationObj:","Scale");
    }

    @Override
    protected void play(AnimationSet animationSet) {
        super.play(animationSet);
        ScalePlay(animationSet);
        Log.d("AnimationObj:","Scale："+"play");
    }


    private void ScalePlay(AnimationSet animationSet) {
        /**
         参数解释：
         第一个参数：X轴水平缩放起始位置的大小（fromX）。1代表正常大小
         第二个参数：X轴水平缩放完了之后（toX）的大小，0代表完全消失了
         第三个参数：Y轴垂直缩放起始时的大小（fromY）
         第四个参数：Y轴垂直缩放结束后的大小（toY）
         第五个参数：pivotXType为动画在X轴相对于物件位置类型
         第六个参数：pivotXValue为动画相对于物件的X坐标的开始位置
         第七个参数：pivotXType为动画在Y轴相对于物件位置类型
         第八个参数：pivotYValue为动画相对于物件的Y坐标的开始位置

         （第五个参数，第六个参数），（第七个参数,第八个参数）是用来指定缩放的中心点
         0.5f代表从中心缩放
         */
        ScaleAnimation scaleAnimation = null;
        switch (scaleModel) {
            case AnimationUtil.ScaleBig:
                scaleAnimation = new ScaleAnimation(0, 1f, 0, 1,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                scaleAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
                break;
            case AnimationUtil.ScaleSmall:
                scaleAnimation = new ScaleAnimation(1f, 0, 1f, 0,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                scaleAnimation.setInterpolator(new AccelerateInterpolator());
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


        scaleAnimation.setFillAfter(true);
        //1.5秒完成动画
        scaleAnimation.setDuration(500);
        //将AlphaAnimation这个已经设置好的动画添加到 AnimationSet中
        animationSet.addAnimation(scaleAnimation);

    }
}

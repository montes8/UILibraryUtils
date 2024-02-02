package com.gb.vale.uitaylibrary.animation

import android.animation.Animator
import android.animation.ValueAnimator
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import com.gb.vale.uitaylibrary.utils.uiTayGone
import com.gb.vale.uitaylibrary.utils.uiTayHandler
import com.gb.vale.uitaylibrary.utils.uiTayVisible


/** The view disappears with an animation that contracts from top to bottom*/
fun View.uiTayScaleUpView(duration: Int = 500){
    val height = this.height
    uiTayExtendHeightView(height, 0, this,
        duration, object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {
                //not code
            }
            override fun onAnimationEnd(animator: Animator) { uiTayGone()}

            override fun onAnimationCancel(animator: Animator) {
                //not code
            }
            override fun onAnimationRepeat(animator: Animator) {
                //not code
            }
        }).start()

}

/**A distance value is assigned from which it will slide to its real position from bottom to top.*/
fun View.uiTaySlideUp(duration: Long = 500,heightInit : Int = 850) {

    this@uiTaySlideUp.uiTayVisible()
    val animate = TranslateAnimation(
        0f,  // fromXDelta
        0f,  // toXDelta
        (this.height + heightInit).toFloat(),  // fromYDelta
        0f) // toYDelta
    animate.duration = duration
    animate.fillAfter = true
    this.startAnimation(animate)
}

/**assign a higher height value so that it is completely hidden downwards*/
fun View.uiTaySlideDown(duration: Long = 500,heightInit : Int = 850) {
    val animate = TranslateAnimation(
        0f,  // fromXDelta
        0f,  // toXDelta
        0f,  // fromYDelta
        (
                this.height + heightInit).toFloat()
    ) // toYDelta
    animate.duration = duration
    animate.fillAfter = true
    animate.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation) {}
        override fun onAnimationEnd(animation: Animation) {
            this@uiTaySlideDown.uiTayGone()
        }
        override fun onAnimationRepeat(animation: Animation) {}
    })

    this.startAnimation(animate)
}


fun uiTayExtendHeightView(
    start: Int,
    end: Int,
    view: View,
    duration: Int,
    animatorListener: Animator.AnimatorListener?
): ValueAnimator {
    val animator = ValueAnimator.ofInt(start, end)
    animator.addUpdateListener { valueAnimator ->
        val value = valueAnimator.animatedValue as Int
        val layoutParams = view.layoutParams
        layoutParams.height = value
        view.layoutParams = layoutParams
    }
    animator.duration = duration.toLong()
    if (animatorListener != null)
        animator.addListener(animatorListener)
    return animator
}
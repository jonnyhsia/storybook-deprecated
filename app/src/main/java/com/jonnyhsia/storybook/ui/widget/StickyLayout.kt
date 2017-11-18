package com.jonnyhsia.storybook.ui.widget

import android.animation.ValueAnimator
import android.content.Context
import android.support.v4.view.NestedScrollingParent
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import android.view.ViewConfiguration
import android.view.animation.Interpolator
import android.widget.LinearLayout
import android.widget.OverScroller

import com.jonnyhsia.storybook.R

class StickyLayout(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs), NestedScrollingParent {

    override fun onStartNestedScroll(child: View, target: View, nestedScrollAxes: Int): Boolean {
        // Log.e(TAG, "onStartNestedScroll")
        return true
    }

    override fun onNestedScrollAccepted(child: View, target: View, nestedScrollAxes: Int) {
        // Log.e(TAG, "onNestedScrollAccepted")
    }

    override fun onStopNestedScroll(target: View) {
        // Log.e(TAG, "onStopNestedScroll")
    }

    override fun onNestedScroll(target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int) {
        // Log.e(TAG, "onNestedScroll")
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray) {
        // Log.e(TAG, "onNestedPreScroll")
        val hiddenTop = dy > 0 && scrollY < topViewHeight
        val showTop = dy < 0 && scrollY >= 0 && !target.canScrollVertically(-1)

        if (hiddenTop || showTop) {
            scrollBy(0, dy)
            consumed[1] = dy
        }
    }

    private val TOP_CHILD_FLING_THRESHOLD = 3

    override fun onNestedFling(target: View, velocityX: Float, velocityY: Float, consumed: Boolean): Boolean {
        var isConsumed = consumed
        // 如果是 recyclerView 根据判断第一个元素是哪个位置可以判断是否消耗
        // 这里判断如果第一个元素的位置是大于 TOP_CHILD_FLING_THRESHOLD 的
        // 认为已经被消耗，在 animateScroll 里不会对 velocityY < 0 时做处理
        if (target is RecyclerView && velocityY < 0) {
            val firstChild = target.getChildAt(0)
            val childAdapterPosition = target.getChildAdapterPosition(firstChild)
            isConsumed = childAdapterPosition > TOP_CHILD_FLING_THRESHOLD
        }
        if (!isConsumed) {
            animateScroll(velocityY, computeDuration(0f), isConsumed)
        } else {
            animateScroll(velocityY, computeDuration(velocityY), isConsumed)
        }
        return true
    }

    override fun onNestedPreFling(target: View, velocityX: Float, velocityY: Float): Boolean {
        //不做拦截 可以传递给子View
        return false
    }

    override fun getNestedScrollAxes(): Int {
        // Log.e(TAG, "getNestedScrollAxes")
        return 0
    }

    /**
     * 根据速度计算滚动动画持续时间
     *
     * @param velocityY
     * @return
     */
    private fun computeDuration(velocityY: Float): Int {
        var y = velocityY
        val distance: Int
        distance = if (y > 0) {
            Math.abs(topView!!.height - scrollY)
        } else {
            Math.abs(topView!!.height - (topView!!.height - scrollY))
        }


        val duration: Int
        y = Math.abs(y)
        duration = if (y > 0) {
            3 * Math.round(1000 * (distance / y))
        } else {
            val distanceRatio = distance.toFloat() / height
            ((distanceRatio + 1) * 150).toInt()
        }

        return duration

    }

    private fun animateScroll(velocityY: Float, duration: Int, consumed: Boolean) {
        val currentOffset = scrollY
        val topHeight = topView!!.height
        if (offsetAnimator == null) {
            offsetAnimator = ValueAnimator()
            offsetAnimator!!.interpolator = interpolator
            offsetAnimator!!.addUpdateListener { animation ->
                if (animation.animatedValue is Int) {
                    scrollTo(0, animation.animatedValue as Int)
                }
            }
        } else {
            offsetAnimator!!.cancel()
        }
        offsetAnimator!!.duration = Math.min(duration, 600).toLong()

        if (velocityY >= 0) {
            offsetAnimator!!.setIntValues(currentOffset, topHeight)
            offsetAnimator!!.start()
        } else {
            //如果子View没有消耗down事件 那么就让自身滑倒0位置
            if (!consumed) {
                offsetAnimator!!.setIntValues(currentOffset, 0)
                offsetAnimator!!.start()
            }

        }
    }

    private var topView: View? = null
    private var stickyView: View? = null
    private var recyclerView: RecyclerView? = null

    private var topViewHeight: Int = 0

    private val scroller: OverScroller
    // private var velocityTracker: VelocityTracker? = null
    private var offsetAnimator: ValueAnimator? = null
    private val interpolator: Interpolator? = null
    private val touchSlop: Int
    private val maxVelocity: Int
    private val minVelocity: Int

    // private val lastY: Float = 0f
    // private val dragging: Boolean = false

    init {
        orientation = LinearLayout.VERTICAL

        scroller = OverScroller(context)
        touchSlop = ViewConfiguration.get(context).scaledTouchSlop
        maxVelocity = ViewConfiguration.get(context)
                .scaledMaximumFlingVelocity
        minVelocity = ViewConfiguration.get(context)
                .scaledMinimumFlingVelocity

    }

    /*private fun initVelocityTrackerIfNotExists() {
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain()
        }
    }

    private fun recycleVelocityTracker() {
        if (velocityTracker != null) {
            velocityTracker?.recycle()
            velocityTracker = null
        }
    }*/

    override fun onFinishInflate() {
        super.onFinishInflate()
        topView = findViewById(R.id.topView)
        stickyView = findViewById(R.id.stickyView)
        val view = findViewById<View>(R.id.scrollableView) as? RecyclerView ?: throw RuntimeException()
        recyclerView = view
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // 不限制顶部的高度
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        getChildAt(0).measure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
        val params = recyclerView!!.layoutParams
        params.height = measuredHeight - stickyView!!.measuredHeight
        setMeasuredDimension(measuredWidth, topView!!.measuredHeight + stickyView!!.measuredHeight + recyclerView!!.measuredHeight)
    }

    override fun onSizeChanged(w: Int, h: Int, oldW: Int, oldH: Int) {
        super.onSizeChanged(w, h, oldW, oldH)
        topViewHeight = topView?.measuredHeight ?: 0
    }

    /*fun fling(velocityY: Int) {
        scroller.fling(0, scrollY, 0, velocityY, 0, 0, 0, topViewHeight)
        invalidate()
    }*/

    override fun scrollTo(x: Int, y: Int) {
        var newY = y
        if (newY < 0) {
            newY = 0
        }
        if (newY > topViewHeight) {
            newY = topViewHeight
        }
        if (newY != scrollY) {
            super.scrollTo(x, newY)
        }
    }

    override fun computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(0, scroller.currY)
            invalidate()
        }
    }

    /*companion object {
        private val TAG = "StickyNavLayout"
    }*/
}

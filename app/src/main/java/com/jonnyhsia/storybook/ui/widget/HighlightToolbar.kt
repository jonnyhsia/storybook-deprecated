package com.jonnyhsia.storybook.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.jonnyhsia.storybook.R

/**
 * Created by JonnyHsia on 17/6/28.
 * 醒目标题与会自动提示的按钮 的自定义 Toolbar
 */
class HighlightToolbar : FrameLayout {

    var title: String? = null
        set(value) {
            field = value
            mTvTitle?.text = value
        }
    var subTitle: String? = null
        set(value) {
            field = value
            mTvSubTitle?.text = value
        }
    private var resource: Int = -1
        set(value) {
            if (value != -1) {
                field = value
                mImgButton?.setImageResource(value)
            }
        }

    private var mTvTitle: TextView? = null
    private var mTvSubTitle: TextView? = null
    private var mImgButton: ImageView? = null

    var mButtonClick: ((View) -> Unit)? = null

    constructor(context: Context) : super(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs, 0) {
        initView(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context, attrs)
    }

    private fun initView(context: Context, attrs: AttributeSet?) {
        LayoutInflater.from(context).inflate(R.layout.view_hightlight_toolbar, this, true)
        mTvTitle = findViewById(R.id.tvTitle)
        mTvSubTitle = findViewById(R.id.tvSubTitle)
        mImgButton = findViewById(R.id.imgButton)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.HighlightToolbar)
        // 获取自定义控件的属性
        title = typedArray.getString(R.styleable.HighlightToolbar_highlightTitle)
        subTitle = typedArray.getString(R.styleable.HighlightToolbar_highlightSubTitle)
        resource = typedArray.getResourceId(R.styleable.HighlightToolbar_icon, -1)
        typedArray.recycle()

        /*val underline = resources.getDrawable(R.drawable.underline)
        underline.setBounds(0, 0, 2000, 4)
        underline.setTint(resources.getColor(R.color.iconTint))
        mTvTitle?.setCompoundDrawables(null, null, null, underline)
        mTvTitle?.compoundDrawablePadding = UIUtils.dp2px(10f, context).toInt()*/

        mImgButton?.setOnClickListener {
            mButtonClick?.invoke(it)
        }
    }

    /*fun nestedOffset(scrolledY: Float): Unit {
        if (measuredHeight < scrolledY) {
            return
        }
        val p = (measuredHeight - scrolledY) / measuredHeight
        mTvTitle?.textSize = 20f + 18f * p
    }*/
}
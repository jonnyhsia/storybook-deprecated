package com.jonnyhsia.storybook.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import com.jonnyhsia.storybook.app.App

/**
 * Created by JonnyHsia on 17/10/30.
 */
class NotoTextView : TextView {

    constructor(context: Context) : super(context) {
        initAttrs(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initAttrs(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initAttrs(context, attrs)
    }

    @Suppress("UNUSED_PARAMETER")
    private fun initAttrs(context: Context, attrs: AttributeSet?) {
        typeface = App.TYPEFACE
        includeFontPadding = false
    }
}

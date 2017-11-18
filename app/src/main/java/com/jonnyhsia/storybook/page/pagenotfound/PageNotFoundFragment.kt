package com.jonnyhsia.storybook.page.pagenotfound

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import com.bumptech.glide.Glide

import com.jonnyhsia.storybook.R
import com.jonnyhsia.storybook.helper.KeyboardChangeListener
import com.jonnyhsia.storybook.page.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_page_not_found.imgPageNotFound
import kotlinx.android.synthetic.main.fragment_page_not_found.imgSendFeedback
import kotlinx.android.synthetic.main.fragment_page_not_found.inputFeedback
import kotlinx.android.synthetic.main.fragment_page_not_found.tvPageNotFound
import kotlinx.android.synthetic.main.fragment_page_not_found.tvPageNotFoundDescription

class PageNotFoundFragment : BaseFragment<PageNotFoundContract.Presenter>(), PageNotFoundContract.View {

    private var keyboardChangeListener: KeyboardChangeListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
            = inflater.inflate(R.layout.fragment_page_not_found, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        Glide.with(this)
                .load(R.mipmap.img_page_not_found)
                .into(imgPageNotFound)
        imgSendFeedback.setOnClickListener {
            presenter.clickFeedback(inputFeedback.text.toString())
        }
    }

    override fun onResume() {
        super.onResume()
        keyboardChangeListener = KeyboardChangeListener(activity)
        keyboardChangeListener?.onKeyboardChange = { isShow, _ ->
            animate(isShow, tvPageNotFound, tvPageNotFoundDescription)
        }
    }

    override fun onStop() {
        super.onStop()
        keyboardChangeListener?.destroy()
    }

    /**
     * 根据 keyboard 状态显隐文字
     */
    private fun animate(isShow: Boolean, vararg animatedView: View) {
        val alpha: Float
        val duration: Long
        if (isShow) {
            alpha = 0f
            duration = 360L
        } else {
            alpha = 1f
            duration = 480L
        }
        for (view in animatedView) {
            view.animate().alpha(alpha)
                    .setDuration(duration)
                    .setInterpolator(AccelerateDecelerateInterpolator())
                    .start()
        }
    }
}
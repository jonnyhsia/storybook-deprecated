package com.jonnyhsia.storybook.page.main.timeline.binder

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.jonnyhsia.storybook.R
import com.jonnyhsia.storybook.helper.find
import com.jonnyhsia.storybook.biz.entity.Story

import me.drakeet.multitype.ItemViewBinder
import android.animation.AnimatorListenerAdapter
import android.view.animation.DecelerateInterpolator
import android.animation.Animator
import android.annotation.SuppressLint
import com.jonnyhsia.storybook.helper.Kits
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Created by JonnyHsia on 17/10/30.
 * 首页故事 View Binder
 */
class StoryViewBinder(private val onStoryClicked: (pos: Int, story: Story) -> Unit)
    : ItemViewBinder<Story, StoryViewBinder.ViewHolder>() {

    private val dateFormat = SimpleDateFormat("MMdd", Locale.CHINA)

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        val root = inflater.inflate(R.layout.item_timeline_story, parent, false)
        return ViewHolder(root)
    }

    private var animationsLocked = false

    private var lastAnimatedPosition = -1

    private var delayEnterAnimation = true

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, story: Story) {
        holder.tvStoryTitle?.text = "${dateFormat.format(story.createTime)}  ${story.title}"
        holder.tvStoryContent?.text = Kits.General.deleteBlankLine(story.content)
        holder.itemView.setOnClickListener {
            onStoryClicked(holder.adapterPosition, story)
        }

        // 确保仅屏幕一开始能够显示的 item 项才开启动画
        if (animationsLocked) {
            return
        }
        val position = holder.adapterPosition
        if (position > lastAnimatedPosition) {
            lastAnimatedPosition = position
            holder.itemView.translationY = 480f
            holder.itemView.alpha = 0f
            // 每个 item 项两个动画，从透明到不透明，从下方移动到原来的位置
            // 并且根据 item 的位置设置延迟的时间，达到一个接着一个的效果
            holder.itemView.animate()
                    .translationY(0f).alpha(1f)
                    .setStartDelay(if (delayEnterAnimation) {
                        18L * position
                    } else {
                        0
                    })
                    .setInterpolator(DecelerateInterpolator(1.5f))
                    .setDuration(320)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            // 确保仅屏幕一开始能够显示的 item 项才开启动画
                            // 也就是说屏幕下方还没有显示的 item 项滑动时是没有动画效果
                            animationsLocked = true
                        }
                    })
                    .start()
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvStoryTitle = find<TextView>(R.id.tvStoryTitle)
        val tvStoryContent = find<TextView>(R.id.tvStoryContent)
    }
}

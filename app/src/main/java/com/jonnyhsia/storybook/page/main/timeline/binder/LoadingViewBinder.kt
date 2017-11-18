package com.jonnyhsia.storybook.page.main.timeline.binder

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.jonnyhsia.storybook.R
import com.jonnyhsia.storybook.helper.find
import me.drakeet.multitype.ItemViewBinder

/**
 * Created by JonnyHsia on 17/10/31.
 * 全量加载中 View Binder
 */
class LoadingViewBinder : ItemViewBinder<LoadingViewBinder.LoadingEntity, LoadingViewBinder.ViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        val root = inflater.inflate(R.layout.item_timeline_complete_loading, parent, false)
        return ViewHolder(root)
    }

    override fun onBindViewHolder(holder: ViewHolder, entity: LoadingEntity) {
        Glide.with(holder.itemView)
                .load(R.mipmap.img_timeline_loading)
                .into(holder.imgLoading)
        // 播放加载动画
        AnimationUtils.loadAnimation(holder.itemView.context, R.anim.show_and_hide)
                .apply {
                    repeatCount = Animation.INFINITE
                    repeatMode = Animation.REVERSE
                }
                .let {
                    holder.itemView.startAnimation(it)
                }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgLoading = find<ImageView>(R.id.imgLoading)
    }

    /**
     * 仅用于注册类型池
     */
    class LoadingEntity
}
package com.jonnyhsia.storybook.page.main.timeline.binder

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.jonnyhsia.storybook.R

import me.drakeet.multitype.ItemViewBinder

/**
 * Created by JonnyHsia on 17/10/30.
 * 空页 View Binder
 */
class EmptyViewBinder(var goCreate: () -> Unit) : ItemViewBinder<EmptyViewBinder.EmptyEntity, EmptyViewBinder.ViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        val root = inflater.inflate(R.layout.item_timeline_empty, parent, false)
        return ViewHolder(root)
    }

    override fun onBindViewHolder(holder: ViewHolder, entity: EmptyViewBinder.EmptyEntity) {

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    class EmptyEntity {

    }
}

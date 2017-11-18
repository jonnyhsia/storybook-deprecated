package com.jonnyhsia.storybook.ui

import android.graphics.Canvas
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import com.jonnyhsia.storybook.app.App
import com.jonnyhsia.storybook.R
import com.jonnyhsia.storybook.page.main.timeline.binder.LoadingViewBinder

/**
 * Created by JonnyHsia on 17/11/5.
 * 滑动 RecyclerView Item
 */
class SwipeItemTouchHelper private constructor(callback: ItemTouchHelper.Callback) : ItemTouchHelper(callback) {

    constructor(onSwipeToEnd: (pos: Int) -> Unit) : this(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.END) {

        override fun onMove(recyclerView: RecyclerView,
                            viewHolder: RecyclerView.ViewHolder,
                            target: RecyclerView.ViewHolder) = false

        override fun onChildDraw(c: Canvas?,
                                 recyclerView: RecyclerView?,
                                 viewHolder: RecyclerView.ViewHolder?,
                                 dX: Float,
                                 dY: Float,
                                 actionState: Int,
                                 isCurrentlyActive: Boolean) {
            if (viewHolder == null || viewHolder is LoadingViewBinder.ViewHolder) {
                return
            }

            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE && isCurrentlyActive) {
                val itemView = viewHolder.itemView
                val d = ContextCompat.getDrawable(App.INSTANCE, R.drawable.swipe_drawable)
                d.setBounds(itemView.left, itemView.top, dX.toInt(), itemView.bottom)
                d.draw(c)
            }
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder,
                              direction: Int) {
            when (direction) {
                END -> onSwipeToEnd(viewHolder.adapterPosition)
            }
        }
    })
}

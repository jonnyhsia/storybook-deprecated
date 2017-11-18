package com.jonnyhsia.storybook.page.main.timeline

import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.BaseTransientBottomBar
import android.support.design.widget.Snackbar
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jonnyhsia.storybook.R
import com.jonnyhsia.storybook.helper.UIKits
import com.jonnyhsia.storybook.biz.entity.Story
import com.jonnyhsia.storybook.page.base.BaseFragment
import com.jonnyhsia.storybook.page.main.timeline.binder.LoadingViewBinder
import com.jonnyhsia.storybook.page.main.timeline.binder.EmptyViewBinder
import com.jonnyhsia.storybook.page.main.timeline.binder.StoryViewBinder
import com.jonnyhsia.storybook.ui.SnackbarCompat
import com.jonnyhsia.storybook.ui.SwipeItemTouchHelper
import kotlinx.android.synthetic.main.fragment_timeline.scrollableView
import kotlinx.android.synthetic.main.fragment_timeline.stickyView
import kotlinx.android.synthetic.main.fragment_timeline.topView
import me.drakeet.multitype.Items
import me.drakeet.multitype.MultiTypeAdapter

/**
 * Created by JonnyHsia on 17/10/29.
 * 用户时间线
 */
class TimelineFragment : BaseFragment<TimelineContract.Presenter>(), TimelineContract.View {

    private val timelineAdapter = MultiTypeAdapter(Items())

    /**
     * 故事点击的事件
     */
    private val onStoryClicked = { pos: Int, _: Story -> presenter.readStory(pos) }

    /**
     * 故事滑动的事件
     */
    private val onStorySwiped = { pos: Int -> presenter.prepareToDeleteStory(pos) }

    /**
     * 创作故事点击的事件
     */
    private val onCreateStoryClicked = { presenter.clickCreateStory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View
            = inflater.inflate(R.layout.fragment_timeline, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // 注册 Timeline 的 item type
        timelineAdapter.register(LoadingViewBinder.LoadingEntity::class.java, LoadingViewBinder())
        timelineAdapter.register(EmptyViewBinder.EmptyEntity::class.java, EmptyViewBinder(onCreateStoryClicked))
        timelineAdapter.register(Story::class.java, StoryViewBinder(onStoryClicked))
        scrollableView?.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            itemAnimator = DefaultItemAnimator()
            adapter = timelineAdapter
        }
        SwipeItemTouchHelper(onStorySwiped).attachToRecyclerView(scrollableView)

        stickyView?.compoundDrawablesRelative?.getOrNull(index = 0)?.let {
            @Suppress("DEPRECATION")
            UIKits.tintDrawable(drawable = it, colors = resources.getColor(R.color.textCaption))
        }
    }

    override fun showLoading() {
        timelineAdapter.items = arrayListOf(LoadingViewBinder.LoadingEntity())
        timelineAdapter.notifyDataSetChanged()
    }

    override fun showLoadingSuccess(timelineData: List<Story>) {
        timelineAdapter.items = timelineData
        timelineAdapter.notifyDataSetChanged()
    }

    override fun showCachedData(cachedTimelineData: List<Story>) {
        timelineAdapter.items = cachedTimelineData
        timelineAdapter.notifyDataSetChanged()
    }

    override fun showEmptyState() {

    }

    override fun showLoadingError(@StringRes errorMsg: Int) {

    }

    override fun showSubTitle(subTitle: String) {
        topView.subTitle = subTitle
    }

    // 恢复故事
    override fun restoreStory(items: Items, pos: Int) {
        timelineAdapter.items = items
        timelineAdapter.notifyItemInserted(pos)
        scrollableView.smoothScrollToPosition(pos)
    }

    // 移除故事
    override fun showRemoveStory(items: Items, pos: Int) {
        timelineAdapter.items = items
        timelineAdapter.notifyItemRemoved(pos)

        SnackbarCompat.make(view ?: return, "删除了一篇故事")
            .setAction("撤销", {
                presenter.cancelDeleteStory(pos)
            })
            .addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
                override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                    super.onDismissed(transientBottomBar, event)
                    // 判断 event 类型
                    // 非 Action 引起的 Dismiss 则进行删除操作
                    if (event == BaseTransientBottomBar.BaseCallback.DISMISS_EVENT_ACTION) {
                        return
                    }
                    presenter.deleteStory()
                }
            })
            .show()
    }

    override fun showLoadedComplete() {
        TODO("not implemented")
    }
}
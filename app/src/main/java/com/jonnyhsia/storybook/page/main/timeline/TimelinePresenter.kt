package com.jonnyhsia.storybook.page.main.timeline

import android.os.Handler
import com.jonnyhsia.storybook.helper.Account
import com.jonnyhsia.storybook.helper.TimeKits
import com.jonnyhsia.storybook.biz.Injection
import com.jonnyhsia.storybook.biz.entity.Story
import com.jonnyhsia.storybook.biz.story.StoryRepository
import com.jonnyhsia.storybook.router.Router
import io.reactivex.disposables.CompositeDisposable
import me.drakeet.multitype.Items
import java.util.LinkedList

class TimelinePresenter(private val view: TimelineContract.View) : TimelineContract.Presenter {

    private val storyRepository: StoryRepository = Injection.getStoryRepository()

    private var pageOffset = 0

    private val disposable = CompositeDisposable()

    /**
     * 请求的故事页数
     */
    private var offset = 0

    /**
     * View 层显示的数据集合
     */
    private val storyList = Items()

    /**
     * 删除的队列
     */
    private val deleteQueue = DeleteQueue<Story>()

    override fun start() {
        // 显示正在加载
        view.showLoading()
    }

    override fun resume() {
        // 显示时间线的副标题
        view.showSubTitle(TimeKits.getStandardDate())
        requestTimeline()
    }

    /**
     * 请求服务器时间线的数据
     */
    override fun requestTimeline() {
        val username = "supotato"
        val startTime = System.currentTimeMillis()
        storyRepository.getUserTimeline(
                username = username,
                offset = pageOffset,
                limit = 20,
                onSubscribe = {
                    disposable.add(it)
                },
                onEmpty = {
                    // 若请求成功则关闭 Disposable 所管理的连接
                    disposable.clear()
                    if (pageOffset == 0) {
                        view.showEmptyState()
                        return@getUserTimeline
                    }
                    // 不再加载更多
                    view.showLoadedComplete()
                },
                onSuccess = {
                    // 若请求成功则关闭 Disposable 所管理的连接
                    disposable.clear()
                    offset++
                    storyList.clear()
                    storyList.addAll(it)
                    // 保证动画执行时间
                    Handler().postDelayed({
                        view.showLoadingSuccess(it)
                    }, maxOf(0, 2500 - (System.currentTimeMillis() - startTime)))
                },
                onError = {

                })
    }

    /**
     * 准备删除故事
     */
    override fun prepareToDeleteStory(pos: Int) {
        if (pos >= storyList.size) {
            return
        }
        // 取出最新放入删除队列的故事
        // 排除 null 与非 Story 类型的可能
        val story = storyList.removeAt(pos) as? Story ?: return
        deleteQueue.put(story)

        view.showRemoveStory(storyList, pos)
    }

    /**
     * TODO 关于参数 pos 的思考
     * 让 view 来告诉 presenter 故事的 pos 是否合理?
     * 还是让 presenter 来维护删除队列中的故事删除前的 pos?
     */
    override fun cancelDeleteStory(pos: Int) {
        val story = deleteQueue.getNewest()
        storyList.add(pos, story)
        view.restoreStory(storyList, pos)
    }

    /**
     * 确定删除故事
     */
    override fun deleteStory() {
        val story = deleteQueue.get() ?: return
        storyRepository.deleteStory(story = story,
                onSubscribe = {
                    disposable.add(it)
                },
                onSuccess = {
                    view.showMessage("删除故事成功: $it")
                },
                onFailed = {
                    view.showMessage("删除故事失败: ${it.message}")
                })
    }

    override fun readStory(pos: Int) {
        // view.jump()
    }

    override fun clickSearchBar() {
        // view.jump()
    }

    override fun clickAvatar() {
        // view.jump()
    }

    override fun clickCreateStory() {
        view.jump(Router.URI_CREATE_STORY)
    }

    override fun loadMoreStories() {
        // TODO 加载更多故事
    }

    override fun refreshTimeline() {
        // TODO 刷新数据
        offset = 0
        view.showLoading()
        requestTimeline()
    }

    override fun destroy() {
        disposable.dispose()
    }

    class DeleteQueue<T> {
        private val linkedList = LinkedList<T>()

        fun get(): T? = linkedList.removeLast()

        fun getNewest(): T? = linkedList.removeFirst()

        fun put(element: T) = linkedList.addFirst(element)

        val size get() = linkedList.size

        fun isEmpty() = linkedList.isEmpty()

        fun clear() {
            linkedList.clear()
        }
    }
}
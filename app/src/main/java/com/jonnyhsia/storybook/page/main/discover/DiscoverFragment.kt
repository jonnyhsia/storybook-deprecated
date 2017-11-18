package com.jonnyhsia.storybook.page.main.discover

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.rubensousa.gravitysnaphelper.GravityPagerSnapHelper
import com.jonnyhsia.storybook.R
import com.jonnyhsia.storybook.biz.entity.GridBanner
import com.jonnyhsia.storybook.page.base.BaseFragment
import com.jonnyhsia.storybook.page.main.discover.binder.GridBannerViewBinder
import kotlinx.android.synthetic.main.fragment_discover.stubBanner
import kotlinx.android.synthetic.main.fragment_discover.viewLoading
import me.drakeet.multitype.Items
import me.drakeet.multitype.MultiTypeAdapter

/**
 * Created by JonnyHsia on 17/10/29.
 * Discover fragment
 */
class DiscoverFragment : BaseFragment<DiscoverContract.Presenter>(), DiscoverContract.View {

    /**
     * 横幅列表适配器
     */
    private val bannerAdapter = MultiTypeAdapter()

    /**
     * Banner Recycler View
     */
    private var rvBanner: RecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
            = inflater.inflate(R.layout.fragment_discover, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bannerAdapter.register(GridBanner::class.java,
                GridBannerViewBinder(onClick = { presenter.clickBanner(it) }))
    }

    override fun showForceLoading() {
        TODO("not implemented")
    }

    override fun showLoadBannerComplete(items: Items) {
        hideProgressIndicator()
        checkBannerInflated()

        bannerAdapter.items = items
        bannerAdapter.notifyDataSetChanged()
    }

    override fun showLoadSubscriptionComplete(items: Items) {
        hideProgressIndicator()
        checkDiscoverSheetInflated()
    }

    /**
     * 隐藏加载界面
     */
    private fun hideProgressIndicator() {
        if (viewLoading.visibility != View.GONE) {
            viewLoading.visibility = View.GONE
        }
    }

    /**
     * 检查 Banner 所在的 ViewStub 是否已经 inflated
     */
    private fun checkBannerInflated() {
        if (rvBanner != null) {
            return
        }
        val viewBanner = stubBanner?.inflate()
        rvBanner = viewBanner?.findViewById(R.id.rvBanner)

        with(rvBanner ?: return) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = bannerAdapter
        }

        GravityPagerSnapHelper(Gravity.START).attachToRecyclerView(rvBanner)
    }

    private fun checkDiscoverSheetInflated() {

    }
}
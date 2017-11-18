package com.jonnyhsia.storybook.page.main.discover.binder

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.load.engine.DiskCacheStrategy

import com.jonnyhsia.storybook.R
import com.jonnyhsia.storybook.app.GlideApp
import com.jonnyhsia.storybook.biz.entity.GridBanner
import com.jonnyhsia.storybook.helper.UIKits
import com.jonnyhsia.storybook.helper.find
import com.jonnyhsia.storybook.ui.CardOutlineProvider
import com.jonnyhsia.uilib.widget.SpannedGridLayoutManager
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

import me.drakeet.multitype.ItemViewBinder

/**
 * Created by JonnyHsia on 17/11/8.
 * Grid Banner View Binder
 */
class GridBannerViewBinder(private val onClick: (pos: Int) -> Unit) : ItemViewBinder<GridBanner, GridBannerViewBinder.ViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        val root = inflater.inflate(R.layout.item_discover_banner_collection, parent, false)
        return ViewHolder(root)
    }

    override fun onBindViewHolder(holder: ViewHolder, banner: GridBanner) {
        val position = holder.adapterPosition
        holder.itemView.outlineProvider = CardOutlineProvider(0f, 0.3f)
        holder.rvBannerItems?.adapter = GridBannerItemsAdapter(banner)
        holder.itemView.setOnClickListener { onClick(position) }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rvBannerItems = find<RecyclerView>(R.id.rvBannerItems)?.apply {
            layoutManager = SpannedGridLayoutManager(SpannedGridLayoutManager.GridSpanLookup { position ->
                when (position) {
                    0, 2 -> SpannedGridLayoutManager.SpanInfo(2, 2)
                    else -> SpannedGridLayoutManager.SpanInfo(1, 1)
                }
            }, 5, 1f)
            setHasFixedSize(true)
        }
    }

    /**
     * Banner 中正方格的适配器
     */
    class GridBannerItemsAdapter(private var banner: GridBanner)
        : RecyclerView.Adapter<GridBannerItemsAdapter.VH>() {

        override fun onBindViewHolder(holder: VH?, position: Int) {
            holder ?: return
            val pos = holder.adapterPosition

            when (pos) {
                0 -> loadImageWithUrl(holder, banner.bannerImgUrl)
                itemCount - 1 -> loadImageWithLocalRes(holder, R.mipmap.ic_arrow)
                else -> loadImageWithUrl(holder, banner.bannerItems[position - 1].imgUrl)
            }
        }

        private fun loadImageWithLocalRes(holder: VH, res: Int) {
            val radius = UIKits.dp2px(4f, holder.itemView.context).toInt()
            GlideApp.with(holder.itemView)
                .load(res)
                .centerCrop()
                .transform(RoundedCornersTransformation(radius, 0, RoundedCornersTransformation.CornerType.ALL))
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(holder.imgGridBannerItem)
        }

        private fun loadImageWithUrl(holder: VH, imgUrl: String) {
            val radius = UIKits.dp2px(4f, holder.itemView.context).toInt()
//            val url = "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=454870969,2857703981&fm=27&gp=0.jpg"
            GlideApp.with(holder.itemView)
                .load(imgUrl)
                .centerCrop()
                .transform(RoundedCornersTransformation(radius, 0, RoundedCornersTransformation.CornerType.ALL))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imgGridBannerItem)
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): VH {
            val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_grid_banner_item, parent, false)
            return VH(view)
        }

        override fun getItemCount() = banner.bannerItems.size + 2

        class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val imgGridBannerItem = find<ImageView>(R.id.imgGridBannerItem)
        }
    }
}

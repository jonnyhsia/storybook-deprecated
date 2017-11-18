package com.jonnyhsia.storybook.biz.entity

import java.util.Date

/**
 * Created by JonnyHsia on 17/10/29.
 * Story Entity
 */
data class Story(var storyId: Long,
                 var title: String,
                 var content: String,
                 var author: String,
                 var images: String, var createTime: Date) {

    override fun equals(other: Any?): Boolean {
        return if (other is Story) {
            storyId == other.storyId
                    && title == other.title
                    && content == other.content
                    && images == other.images
        } else {
            super.equals(other)
        }
    }

    override fun hashCode(): Int {
        var result = storyId.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + content.hashCode()
        result = 31 * result + images.hashCode()
        return result
    }

    /**
     * 将字符串转换成 [List]
     */
    fun imagesArray() = images.split(delimiters = ";")

    /**
     * 将传入的 [List] 转换成字符串
     * @param imgs 图片的 URL 数据
     */
    fun setImageArray(imgs: List<String>) {
        val builder = StringBuilder()
        for (i in 0 until imgs.size) {
            builder.append(imgs[i])
            if (i == imgs.size - 1) {
                builder.append(";")
            }
        }
        images = builder.toString()
    }

    companion object {
        fun generateSimpleStory(id: Long) = Story(id, "ASDGH ASDG ASDASH DASV", "sidoasljdl uashdk h h hakjshd ,jadmop;dk;sa,/dsfkl; k ;k ds;fkdsf sdf asdKLkldjsklj sj dja lsd jasldjakslf sjdklfjlsdjflsdj lflskd jf", "", "", Date())
    }
}
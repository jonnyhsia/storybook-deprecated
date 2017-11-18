package com.jonnyhsia.storybook.biz.entity

/**
 * Created by JonnyHsia on 17/8/19.
 * HTTP 对返回数据的包装
 */
class Response<T> {
    var success = false
    var data: T? = null
    var error: String? = null

    constructor(success: Boolean, data: T) {
        this.success = success
        this.data = data
    }

    constructor(success: Boolean, error: String) {
        this.success = success
        this.error = error
    }

}
package com.jonnyhsia.storybook.app

/**
 * Created by JonnyHsia on 17/10/29.
 * App 的异常
 */
open class AppException(message: String?,
                        cause: Throwable? = null) : Exception(message, cause)
package com.jonnyhsia.storybook.utils

import android.annotation.SuppressLint
import android.content.Context
import android.preference.PreferenceManager
import com.jonnyhsia.storybook.helper.logd
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by JonnyHsia on 17/8/7.
 * Preference delegate
 */
class Preference<T>(val context: Context?,
                    val name: String,
                    private val default: T,
                    private val onChange: ((newValue: T) -> Unit)? = null)
    : ReadWriteProperty<Any?, T> {

    private val prefs by lazy { PreferenceManager.getDefaultSharedPreferences(context) }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return findPref(name, default)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        setPref(name, value)
        onChange?.invoke(value)
    }

    @SuppressLint("CommitPrefEdits")
    private fun <U> setPref(name: String, value: U) = with(prefs.edit()) {
        logd("$name : $value")
        when (value) {
            is Int -> putInt(name, value)
            is String -> putString(name, value)
            is Float -> putFloat(name, value)
            is Boolean -> putBoolean(name, value)
            is Long -> putLong(name, value)
            else -> throw IllegalArgumentException("This type cannot be saved into Preferences.")
        }.apply()
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> findPref(name: String, default: T): T = with(prefs) {
        val res: Any = when (default) {
            is Int -> getInt(name, default)
            is String -> getString(name, default)
            is Float -> getFloat(name, default)
            is Boolean -> getBoolean(name, default)
            is Long -> getLong(name, default)
            else -> throw IllegalArgumentException("This type cannot be saved into Preferences.")
        }
        res as T
    }

    companion object {
        // 用户
        val USERNAME = "username"
        val NICKNAME = "nickname"
        val EMAIL = "email"
        val AVATAR = "avatar"

        // App
        val IS_NIGHT_MODE = "isNightMode"

        // 故事
        val DRAFT_TITLE = "draft_title"
        val DRAFT_CONTENT = "draft_content"

        /*@Suppress("UNCHECKED_CAST")
        fun <T> getPreference(name: String, default: T): T? {
            var preference: Any? = null
            PreferenceManager.getDefaultSharedPreferences(App.INSTANCE).apply {
                preference = when (default) {
                    is Int -> getInt(name, default)
                    is String -> getString(name, default)
                    is Float -> getFloat(name, default)
                    is Boolean -> getBoolean(name, default)
                    is Long -> getLong(name, default)
                    else -> throw IllegalArgumentException("This type cannot be saved into Preferences.")
                }
            }
            return preference as? T
        }*/
    }
}
package com.jonnyhsia.storybook.helper

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by JonnyHsia on 17/10/29.
 * 工具集合
 */
object Kits {

    object App {
        /**
         * 判断网路是否可用
         */
        fun isNetworkWorking(): Boolean {
            val connectivity = com.jonnyhsia.storybook.app.App.INSTANCE
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = connectivity.activeNetworkInfo
            return activeNetwork != null
        }

        /**
         * 获取应用版本号
         */
        fun getVersionName(context: Context): String {
            return getPackageInfo(context)?.versionName.toString()
        }

        private fun getPackageInfo(context: Context): PackageInfo? {
            var info: PackageInfo? = null
            try {
                info = context.packageManager
                        .getPackageInfo(context.packageName, PackageManager.GET_CONFIGURATIONS)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return info
        }
    }

    object General {

        fun date2tString(date: Date?): String {
            if (date == null) {
                return ""
            }
            val simpleDateFormat = SimpleDateFormat("yy/M/d", Locale.CHINA)
            return simpleDateFormat.format(date)
        }

        fun string2Date(dateString: String?): Date {
            if (dateString == null || dateString.isEmpty()) {
                return Date()
            }
            val simpleDateFormat = SimpleDateFormat("yy/M/d", Locale.CHINA)
            return simpleDateFormat.parse(dateString)
        }

        /***
         * 删除空行与只含空格的行
         * @return
         */
        fun deleteBlankLine(input: String): String {
            return input.replace("((\r\n)|\n)[\\s\t ]*(\\1)+".toRegex(), "$1")
        }

        /**
         * 去除空格空行
         */
        fun deleteBlankSpace(input: String): String {
            return input.replace("\\s*|\t|\r|\n".toRegex(), "")
        }

        fun getFixLengthString(length: Int): String {
            val rm = Random()

            // 获得随机数
            val random = (1 + rm.nextDouble()) * Math.pow(10.0, length.toDouble())
            // 将获得的获得随机数转化为字符串
            val fixLengthString = random.toString()
            // 返回固定的长度的随机数
            return fixLengthString.substring(1, length + 1)
        }

        /**
         * 根据中英文调整字间距
         * 判断字符是否是中文 (待)
         */
        fun isChinese(c: Char): Boolean {
            val ub = Character.UnicodeBlock.of(c);
            return (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                    || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                    || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                    || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                    || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                    || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS)
        }
    }
}
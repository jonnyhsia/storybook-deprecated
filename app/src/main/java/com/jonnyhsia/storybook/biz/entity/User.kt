package com.jonnyhsia.storybook.entity

/**
 * Created by JonnyHsia on 17/8/7.
 * 用户的实体类
 */
class User {
    var username: String = ""
    var nickname: String = ""
    var email: String = ""
    var avatar: String = ""
    var gender: Int = GENDER_OTHER
        set(value) {
            if (value in GENDER_FEMALE..GENDER_OTHER) {
                field = value
            }
        }
    var bonusPoint: Int = 0

    constructor()

    constructor(username: String,
                nickname: String,
                email: String = "",
                avatar: String = "",
                gender: Int = User.GENDER_OTHER) {
        this.username = username
        this.nickname = nickname
        this.email = email
        this.avatar = avatar
        this.gender = gender
    }


    override fun toString(): String {
        return "User {" +
                " username = $username," +
                " nickname = $nickname" +
                " email = $email," +
                " avatar = $avatar," +
                " gender = $gender," +
                " bonus point = $bonusPoint." +
                " }"
    }

    companion object {
        val GENDER_FEMALE = 0
        val GENDER_MALE = 1
        val GENDER_OTHER = 2
    }
}
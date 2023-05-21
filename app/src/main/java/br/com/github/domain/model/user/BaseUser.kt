package br.com.github.domain.model.user

import android.os.Parcelable

interface BaseUser : Parcelable {
    val login: String?
    val id: Int?
    val nodeId: String?
    val avatarUrl: String?
    val gravatarId: String?
    val url: String?
}

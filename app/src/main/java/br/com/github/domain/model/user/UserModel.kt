package br.com.github.domain.model.user

import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    override val login: String?,
    override val id: Int?,
    override val nodeId: String?,
    override val avatarUrl: String?,
    override val gravatarId: String?,
    override val url: String?
) : BaseUser

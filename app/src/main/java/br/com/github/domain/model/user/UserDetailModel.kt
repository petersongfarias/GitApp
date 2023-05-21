package br.com.github.domain.model.user

import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class UserDetailModel(
    override val login: String?,
    override val id: Int?,
    override val nodeId: String?,
    override val avatarUrl: String?,
    override val gravatarId: String?,
    override val url: String?,
    val htmlUrl: String?,
    val followersUrl: String?,
    val followingUrl: String?,
    val gistsUrl: String?,
    val starredUrl: String?,
    val subscriptionsUrl: String?,
    val organizationsUrl: String?,
    val reposUrl: String?,
    val eventsUrl: String?,
    val receivedEventsUrl: String?,
    val type: String?,
    val siteAdmin: Boolean?,
    val name: String?,
    val company: @RawValue Any?,
    val blog: String?,
    val location: String?,
    val email: @RawValue Any?,
    val hireable: Boolean?,
    val bio: String?,
    val twitterUsername: @RawValue Any?,
    val publicRepos: Int?,
    val publicGists: Int?,
    val followers: Int?,
    val following: Int?,
    val createdAt: String?,
    val updatedAt: String?
) : BaseUser

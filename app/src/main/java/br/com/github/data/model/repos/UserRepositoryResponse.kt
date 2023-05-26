package br.com.github.data.model.repos

import br.com.github.data.base.Mapper
import br.com.github.data.model.user.UserResponse
import br.com.github.domain.model.repos.UserRepositoryModel
import com.google.gson.annotations.SerializedName

data class UserRepositoryResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("private")
    val private: Boolean,
    @SerializedName("owner")
    val user: UserResponse,
    @SerializedName("html_url")
    val htmlUrl: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("pushed_at")
    val pushedAt: String?,
    @SerializedName("stargazers_count")
    val stargazersCount: Int?,
    @SerializedName("watchers_count")
    val watchersCount: Int?,
    @SerializedName("forks_count")
    val forksCount: Int?,
    @SerializedName("language")
    val language: String?,
    @SerializedName("license")
    val license: LicenseResponse?,
    @SerializedName("topics")
    val topics: List<Any>?,
    @SerializedName("visibility")
    val visibility: String?
) : Mapper<UserRepositoryModel> {
    override fun mapTo() = UserRepositoryModel(
        id = id,
        name = name,
        private = private,
        user = user.mapTo(),
        htmlUrl = htmlUrl,
        description = description,
        createdAt = createdAt,
        updatedAt = updatedAt,
        pushedAt = pushedAt,
        stargazersCount = stargazersCount ?: 0,
        watchersCount = watchersCount ?: 0,
        forksCount = forksCount ?: 0,
        language = language,
        license = license?.mapTo(),
        topics = topics,
        visibility = visibility
    )
}

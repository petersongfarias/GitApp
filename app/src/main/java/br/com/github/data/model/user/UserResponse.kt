package br.com.github.data.model.user

import br.com.github.data.base.Mapper
import br.com.github.domain.model.user.UserModel
import com.google.gson.annotations.SerializedName

class UserResponse(
    @SerializedName("login")
    val login: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("node_id")
    val nodeId: String?,
    @SerializedName("avatar_url")
    val avatarUrl: String?,
    @SerializedName("gravatar_id")
    val gravatarId: String?,
    @SerializedName("url")
    val url: String?
) : Mapper<UserModel> {
    override fun mapTo() = UserModel(
        login = login,
        id = id,
        nodeId = nodeId,
        avatarUrl = avatarUrl,
        gravatarId = gravatarId,
        url = url
    )
}

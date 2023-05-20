package br.com.github.domain.model.repos

import android.os.Parcelable
import br.com.github.domain.model.user.UserModel
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class UserRepositoryModel(
    val id: Int,
    val name: String,
    val private: Boolean,
    val user: UserModel,
    val htmlUrl: String,
    val description: String,
    val createdAt: String,
    val updatedAt: String,
    val pushedAt: String,
    val stargazersCount: Int,
    val watchersCount: Int,
    val forksCount: Int,
    val language: String,
    val license: LicenseModel?,
    val topics: List<@RawValue Any>,
    val visibility: String
) : Parcelable

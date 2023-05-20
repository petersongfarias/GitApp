package br.com.github.domain.repository

import br.com.github.domain.base.Result
import br.com.github.domain.model.user.UserDetailModel
import br.com.github.domain.model.user.UserModel

interface UserDataRepository {

    suspend fun fetchUserList(
        since: Int,
        perPage: Int
    ): List<UserModel>

    suspend fun fetchUserDetail(
        userName: String
    ): Result<UserDetailModel>
}

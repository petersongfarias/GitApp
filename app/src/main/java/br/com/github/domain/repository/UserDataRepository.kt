package br.com.github.domain.repository

import androidx.paging.Pager
import br.com.github.domain.model.user.UserDetailModel
import br.com.github.domain.model.user.UserModel

interface UserDataRepository {

    suspend fun fetchUserList(
        since: Int,
        perPage: Int
    ): Pager<Int, UserModel>

    suspend fun fetchUserDetail(
        userName: String
    ): Result<UserDetailModel>
}

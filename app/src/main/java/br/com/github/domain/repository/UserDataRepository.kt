package br.com.github.domain.repository

import androidx.paging.Pager
import br.com.github.domain.model.user.UserDetailModel
import br.com.github.domain.model.user.UserModel

interface UserDataRepository {

    suspend fun fetchUsers(): Pager<Int, UserModel>

    suspend fun fetchUserDetail(
        userName: String
    ): Result<UserDetailModel>
}

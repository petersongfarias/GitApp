package br.com.github.data.source

import br.com.github.domain.base.Result
import br.com.github.domain.model.user.UserDetailModel

interface UserDetailRemoteDataSource {

    suspend fun fetchUserDetail(
        userName: String
    ): Result<UserDetailModel>
}

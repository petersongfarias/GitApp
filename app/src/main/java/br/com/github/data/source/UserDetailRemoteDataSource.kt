package br.com.github.data.source

import br.com.github.data.model.user.UserDetailResponse
import retrofit2.Response

interface UserDetailRemoteDataSource {

    suspend fun fetchUserDetail(
        userName: String
    ): Response<UserDetailResponse>
}

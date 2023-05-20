package br.com.github.data.source

import br.com.github.data.model.repos.UserRepositoryResponse
import retrofit2.Response

interface UserRepositoryRemoteDataSource {

    suspend fun fetchRepositoryList(
        userName: String
    ): Response<List<UserRepositoryResponse>>
}

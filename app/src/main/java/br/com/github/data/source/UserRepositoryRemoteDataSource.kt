package br.com.github.data.source

import br.com.github.domain.base.Result
import br.com.github.domain.model.repos.UserRepositoryModel

interface UserRepositoryRemoteDataSource {

    suspend fun fetchRepositoryList(
        userName: String
    ): Result<List<UserRepositoryModel>>
}

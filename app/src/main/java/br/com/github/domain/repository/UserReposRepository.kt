package br.com.github.domain.repository

import br.com.github.domain.model.repos.UserRepositoryModel

interface UserReposRepository {

    suspend fun fetchRepositoryList(
        userName: String
    ): Result<List<UserRepositoryModel>>
}

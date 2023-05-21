package br.com.github.domain.repository

import br.com.github.domain.model.repos.UserRepositoryModel

interface UserRepositoriesRepository {
    suspend fun fetchRepositories(
        userName: String
    ): Result<List<UserRepositoryModel>>
}

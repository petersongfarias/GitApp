package br.com.github.domain.repository

import br.com.github.data.base.logException
import br.com.github.data.base.mapResult
import br.com.github.data.source.UserRepositoryRemoteDataSource

class UserRepositoriesRepositoryImpl(
    private val userRepositoryRemoteDataSource: UserRepositoryRemoteDataSource
) : UserRepositoriesRepository {
    override suspend fun fetchRepositories(
        userName: String
    ) = userRepositoryRemoteDataSource.fetchRepositories(userName)
        .logException("$TAG: fetchRepositories")
        .mapResult {
            it.map { repos -> repos.mapTo() }
        }

    companion object {
        const val TAG = "UserRepositoriesRepositoryImpl"
    }
}

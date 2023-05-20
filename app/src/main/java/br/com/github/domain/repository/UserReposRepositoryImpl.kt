package br.com.github.domain.repository

import br.com.github.data.source.UserRepositoryRemoteDataSource

class UserReposRepositoryImpl(
    private val userRepositoryRemoteDataSource: UserRepositoryRemoteDataSource
) : UserReposRepository {
    override suspend fun fetchRepositoryList(
        userName: String
    ) = userRepositoryRemoteDataSource.fetchRepositoryList(userName)
}

package br.com.github.data.source

import br.com.github.data.service.UserRepositoriesService

class UserRepositoryRemoteDataSourceImpl(
    private val userRepositoryService: UserRepositoriesService
) : UserRepositoryRemoteDataSource {
    override suspend fun fetchRepositories(
        userName: String
    ) = userRepositoryService.fetchRepositories(userName)
}

package br.com.github.data.source

import br.com.github.data.base.getData
import br.com.github.data.service.UserRepositoryService

class UserRepositoryRemoteDataSourceImpl(
    private val userRepositoryService: UserRepositoryService
) : UserRepositoryRemoteDataSource {
    override suspend fun fetchRepositoryList(
        userName: String
    ) = userRepositoryService.fetchRepositoryList(userName).getData()
}

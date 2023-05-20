package br.com.github.domain.repository

import br.com.github.data.base.getData
import br.com.github.data.model.repos.UserRepositoryResponse
import br.com.github.data.service.UserRepositoryService
import br.com.github.domain.base.BaseRepository
import br.com.github.domain.model.repos.UserRepositoryModel

class UserReposRepositoryImpl(
    private val userRepositoryService: UserRepositoryService
) : BaseRepository(), UserReposRepository {
    override suspend fun fetchRepositoryList(
        userName: String
    ) = fetchData<List<UserRepositoryModel>, List<UserRepositoryResponse>> {
        userRepositoryService.fetchRepositoryList(userName).getData()
    }
}

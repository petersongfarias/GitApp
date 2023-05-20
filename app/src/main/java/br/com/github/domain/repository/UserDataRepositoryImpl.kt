package br.com.github.domain.repository

import br.com.github.data.base.getData
import br.com.github.data.service.UserDataService
import br.com.github.domain.base.BaseRepository
import br.com.github.domain.model.user.UserModel

class UserDataRepositoryImpl(
    private val userDataService: UserDataService
) : BaseRepository(), UserDataRepository {

    override suspend fun fetchUserList(since: Int, perPage: Int): List<UserModel> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchUserDetail(userName: String) = userDataService.fetchUser(userName).getData()
}

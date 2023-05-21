package br.com.github.data.source

import br.com.github.data.service.UserDataService

class UserDetailRemoteDataSourceImpl(
    private val userDataService: UserDataService
) : UserDetailRemoteDataSource {
    override suspend fun fetchUserDetail(userName: String) = userDataService.fetchUser(userName)
}

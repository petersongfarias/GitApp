package br.com.github.domain.useCase

import androidx.paging.PagingData
import br.com.github.domain.base.UseCasePager
import br.com.github.domain.model.user.UserModel
import br.com.github.domain.repository.UserDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UsersUseCase(
    private val userDataRepository: UserDataRepository
) : UseCasePager<UsersUseCase.UsersParams, PagingData<UserModel>> {

    class UsersParams(
        val since: Int,
        val perPage: Int
    )

    override suspend fun invoke(param: UsersParams): Flow<PagingData<UserModel>> =
        userDataRepository.fetchUserList(
            since = param.since,
            perPage = param.perPage
        ).flow
}

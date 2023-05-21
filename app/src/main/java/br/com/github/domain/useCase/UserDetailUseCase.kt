package br.com.github.domain.useCase

import br.com.github.domain.base.ResultState
import br.com.github.domain.base.UseCase
import br.com.github.domain.model.user.UserDetailModel
import br.com.github.domain.repository.UserDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserDetailUseCase(
    private val userDataRepository: UserDataRepository
) : UseCase<String, UserDetailModel> {

    override suspend fun invoke(param: String): Flow<ResultState<UserDetailModel>> = flow {
        userDataRepository.fetchUserDetail(param)
            .onSuccess { emit(ResultState.Success(it)) }
            .onFailure { emit(ResultState.Error(message = it.message)) }
    }
}

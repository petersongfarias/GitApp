package br.com.github.domain.useCase

import br.com.github.domain.base.UseCase
import br.com.github.domain.base.ViewState
import br.com.github.domain.model.user.UserDetailModel
import br.com.github.domain.repository.UserDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserDetailUseCase(
    private val userDataRepository: UserDataRepository
) : UseCase<String, UserDetailModel> {

    override suspend fun invoke(param: String): Flow<ViewState<UserDetailModel>> = flow {
        emit(ViewState.Loading())
        userDataRepository.fetchUserDetail(param)
            .onSuccess { emit(ViewState.Success(it)) }
            .onFailure { emit(ViewState.Error(message = it.message)) }
    }
}

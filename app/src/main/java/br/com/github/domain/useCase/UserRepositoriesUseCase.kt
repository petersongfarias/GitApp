package br.com.github.domain.useCase

import br.com.github.domain.base.UseCase
import br.com.github.domain.base.ViewState
import br.com.github.domain.model.repos.UserRepositoryModel
import br.com.github.domain.repository.UserRepositoriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepositoriesUseCase(
    private val repository: UserRepositoriesRepository
) : UseCase<String, List<UserRepositoryModel>> {

    override suspend fun invoke(param: String): Flow<ViewState<List<UserRepositoryModel>>> = flow {
        emit(ViewState.Loading())
        repository.fetchRepositories(param)
            .onSuccess { emit(ViewState.Success(it)) }
            .onFailure { emit(ViewState.Error(message = it.message)) }
    }
}

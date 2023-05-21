package br.com.github.domain.useCase

import br.com.github.domain.base.ResultState
import br.com.github.domain.base.UseCase
import br.com.github.domain.model.repos.UserRepositoryModel
import br.com.github.domain.repository.UserRepositoriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepositoriesUseCase(
    private val repository: UserRepositoriesRepository
) : UseCase<String, List<UserRepositoryModel>> {

    override suspend fun invoke(param: String): Flow<ResultState<List<UserRepositoryModel>>> =
        flow {
            repository.fetchRepositories(param)
                .onSuccess { emit(ResultState.Success(it)) }
                .onFailure { emit(ResultState.Error(message = it.message)) }
        }
}

package br.com.github.domain.useCase

import br.com.github.domain.base.UseCase
import br.com.github.domain.model.repos.UserRepositoryModel
import br.com.github.domain.repository.UserRepositoriesRepository

class UserRepositoriesUseCase(
    private val repository: UserRepositoriesRepository
) : UseCase<String?, List<UserRepositoryModel>> {

    override suspend fun invoke(param: String?): Result<List<UserRepositoryModel>> =
        repository.fetchRepositories(param.orEmpty())
}

package br.com.github.domain.useCase

import br.com.github.domain.base.UseCase
import br.com.github.domain.model.user.UserDetailModel
import br.com.github.domain.repository.UserDataRepository

class UserDetailUseCase(
    private val userDataRepository: UserDataRepository
) : UseCase<String, UserDetailModel> {

    override suspend fun invoke(param: String): Result<UserDetailModel> =
        userDataRepository.fetchUserDetail(param)
}

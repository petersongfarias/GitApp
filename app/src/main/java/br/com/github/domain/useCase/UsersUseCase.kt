package br.com.github.domain.useCase

import androidx.paging.Pager
import br.com.github.domain.base.UseCase
import br.com.github.domain.model.user.UserModel
import br.com.github.domain.repository.UserDataRepository

class UsersUseCase(
    private val userDataRepository: UserDataRepository
) : UseCase<Unit, Pager<Int, UserModel>> {

    override suspend fun invoke(param: Unit): Result<Pager<Int, UserModel>> =
        userDataRepository.fetchUsers()
}

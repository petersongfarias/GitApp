package br.com.github.domain.useCase

import androidx.paging.Pager
import br.com.github.domain.base.UseCasePager
import br.com.github.domain.model.user.UserModel
import br.com.github.domain.repository.UserDataRepository

class UsersUseCase(
    private val userDataRepository: UserDataRepository
) : UseCasePager<Pager<Int, UserModel>> {

    override suspend fun invoke(): Result<Pager<Int, UserModel>> =
        userDataRepository.fetchUsers()
}

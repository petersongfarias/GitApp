package br.com.github.domain.useCase

import androidx.paging.Pager
import br.com.github.domain.model.user.UserModel
import br.com.github.domain.repository.UserDataRepository

class UsersUseCase(
    private val userDataRepository: UserDataRepository
) {

    suspend fun invoke(): Pager<Int, UserModel> = userDataRepository.fetchUsers()
}

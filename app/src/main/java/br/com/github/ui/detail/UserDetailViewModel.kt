package br.com.github.ui.detail

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.ViewModel
import br.com.github.domain.model.user.BaseUser
import br.com.github.domain.useCase.UsersUseCase

class UserDetailViewModel(
    user: BaseUser,
    useCase: UsersUseCase
) : ViewModel(), DefaultLifecycleObserver {



}

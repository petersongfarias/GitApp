package br.com.github.di

import br.com.github.domain.model.user.BaseUser
import br.com.github.ui.detail.UserDetailViewModel
import br.com.github.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        HomeViewModel(
            usersUseCase = get(),
            userDetailUseCase = get()
        )
    }

    viewModel { (user: BaseUser) ->
        UserDetailViewModel(
            user = user,
            userRepositoriesUseCase = get(),
            userDetailUseCase = get()
        )
    }
}

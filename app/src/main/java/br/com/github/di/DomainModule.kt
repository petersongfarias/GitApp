package br.com.github.di

import br.com.github.domain.repository.UserDataRepository
import br.com.github.domain.repository.UserDataRepositoryImpl
import br.com.github.domain.repository.UserRepositoriesRepository
import br.com.github.domain.repository.UserRepositoriesRepositoryImpl
import br.com.github.domain.useCase.UserDetailUseCase
import br.com.github.domain.useCase.UserRepositoriesUseCase
import br.com.github.domain.useCase.UsersUseCase
import org.koin.dsl.module

val repositoryModule = module {
    factory<UserDataRepository> { UserDataRepositoryImpl(get(), get()) }
    factory<UserRepositoriesRepository> { UserRepositoriesRepositoryImpl(get()) }
}

val useCaseModule = module {
    single { UsersUseCase(get()) }
    single { UserDetailUseCase(get()) }
    single { UserRepositoriesUseCase(get()) }
}


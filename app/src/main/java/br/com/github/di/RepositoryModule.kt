package br.com.github.di

import br.com.github.domain.repository.UserDataRepository
import br.com.github.domain.repository.UserDataRepositoryImpl
import br.com.github.domain.repository.UserRepositoriesRepository
import br.com.github.domain.repository.UserRepositoriesRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    factory<UserDataRepository> { UserDataRepositoryImpl(get(), get()) }
    factory<UserRepositoriesRepository> { UserRepositoriesRepositoryImpl(get()) }
}

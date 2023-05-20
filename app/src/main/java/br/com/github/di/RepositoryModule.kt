package br.com.github.di

import br.com.github.domain.repository.UserDataRepository
import br.com.github.domain.repository.UserDataRepositoryImpl
import br.com.github.domain.repository.UserReposRepository
import br.com.github.domain.repository.UserReposRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    factory<UserDataRepository> { UserDataRepositoryImpl(get()) }
    factory<UserReposRepository> { UserReposRepositoryImpl(get()) }
}

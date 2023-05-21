package br.com.github.domain.base

import kotlinx.coroutines.flow.Flow

interface UseCase<T : Any, R : Any> {
    suspend operator fun invoke(param: T): Flow<ViewState<R>>
}

interface UseCasePager<T : Any, R : Any> {
    suspend operator fun invoke(param: T): Flow<R>
}
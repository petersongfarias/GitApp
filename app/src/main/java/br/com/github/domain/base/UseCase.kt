package br.com.github.domain.base

import kotlinx.coroutines.flow.Flow

interface UseCase<T : Any, R : Any> {
    suspend operator fun invoke(param: T): Flow<ResultState<R>>
}

interface UseCasePager<R : Any> {
    suspend operator fun invoke(): R
}
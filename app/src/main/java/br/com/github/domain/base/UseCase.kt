package br.com.github.domain.base

interface UseCase<T : Any, R : Any> {
    suspend operator fun invoke(param: T): Result<R>
}

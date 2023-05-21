package br.com.github.domain.base

sealed class ResultState<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : ResultState<T>(data)
    class Error<T>(message: String?) : ResultState<T>(message = message)
}

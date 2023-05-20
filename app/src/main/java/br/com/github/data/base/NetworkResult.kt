package br.com.github.data.base

import br.com.github.domain.base.Failure
import br.com.github.domain.base.HttpError
import br.com.github.domain.base.Result
import br.com.github.domain.base.Success
import retrofit2.Response

interface Mapper<T : Any> {
    fun mapTo(): T
}

inline fun <T : Any> Response<T>.onSuccess(action: (T) -> Unit): Response<T> {
    if (isSuccessful) body()?.run(action)
    return this
}

inline fun <T : Any> Response<T>.onFailure(action: (HttpError) -> Unit) {
    if (!isSuccessful) errorBody()?.run { action(HttpError(Throwable(message()), code())) }
}

fun <R : Mapper<T>, T : Any> Response<List<R>>.getData(): Result<List<T>> {
    try {
        onSuccess { return Success(it.map { item -> item.mapTo() }) }
        onFailure { return Failure(it) }
        return GenericFailure
    } catch (e: Exception) {
        return GenericFailure
    }
}

fun <R : Mapper<T>, T : Any> Response<R>.getData(): Result<T> {
    try {
        onSuccess { return Success(it.mapTo()) }
        onFailure { return Failure(it) }
        return GenericFailure
    } catch (e: Exception) {
        return GenericFailure
    }
}

private val GenericFailure = Failure(HttpError(Throwable("Não foi possivel carregar suas ingormações no momento")))

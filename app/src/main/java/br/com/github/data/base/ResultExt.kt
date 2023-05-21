package br.com.github.data.base

import kotlinx.coroutines.TimeoutCancellationException
import retrofit2.Response
import timber.log.Timber
import kotlin.coroutines.cancellation.CancellationException

inline fun <T, R> T.resultOf(block: T.() -> R): Result<R> {
    return try {
        Result.success(block())
    } catch (t: TimeoutCancellationException) {
        Result.failure(t)
    } catch (c: CancellationException) {
        throw c
    } catch (e: Exception) {
        Result.failure(e)
    }
}

fun <T> Response<T>.logException(
    tag: String
): Response<T> {
    Timber
        .tag(tag)
        .e(exceptionOrNull() ?: error("Unreachable state"))
    return this
}

inline fun <R, T> Response<T>.mapResult(transform: (value: T) -> R): Result<R> {
    val successResult = if (isSuccessful) body() else null
    return when {
        successResult != null -> resultOf { transform(successResult) }
        else -> Result.failure(exceptionOrNull() ?: error("Unreachable state"))
    }
}

fun <T> Response<T>.exceptionOrNull(): Throwable? {
    return if (!isSuccessful) {
        errorBody()?.let { Throwable(message()) }
    } else null
}

interface Mapper<T : Any> {
    fun mapTo(): T
}

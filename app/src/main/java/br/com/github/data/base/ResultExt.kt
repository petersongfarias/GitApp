package br.com.github.data.base

import android.util.Log
import androidx.paging.LoadState
import kotlinx.coroutines.TimeoutCancellationException
import retrofit2.HttpException
import retrofit2.Response
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
    if (isSuccessful.not()) {
        Log.e(tag, exceptionOrNull()?.message ?: DEFAULT_ERROR_MESSAGE)
    }
    return this
}

inline fun <R, T> Response<T>.mapResult(transform: (value: T) -> R): Result<R> {
    val successResult = if (isSuccessful) body() else null
    return when {
        successResult != null -> resultOf { transform(successResult) }
        else -> Result.failure(exceptionOrNull() ?: error(DEFAULT_ERROR_MESSAGE))
    }
}

fun <T> Response<T>.exceptionOrNull(): Throwable? {
    return if (!isSuccessful) {
        errorBody()?.let { Throwable(message()) }
    } else null
}

fun LoadState.Error?.getErrorMessage(): String {
    if (this == null) {
        return DEFAULT_ERROR_MESSAGE
    }
    return when (val throwable = error) {
        is HttpException -> throwable.response()?.message() ?: DEFAULT_ERROR_MESSAGE

        else -> throwable.message ?: DEFAULT_ERROR_MESSAGE
    }
}

interface Mapper<T : Any> {
    fun mapTo(): T
}

const val DEFAULT_ERROR_MESSAGE = "Não foi possivel carregar suas informações no momento!"

package br.com.github.domain.base

sealed class ViewState<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : ViewState<T>(data)
    class Loading<T>(data: T? = null) : ViewState<T>(data)
    class Error<T>(message: String?) : ViewState<T>(message = message)
}

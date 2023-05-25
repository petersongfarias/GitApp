package br.com.github.ui.common.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState

fun <T> LiveData<T>.observe(owner: LifecycleOwner, onChanged: (T) -> Unit) {
    observe(owner) { onChanged(it) }
}

fun <M> MutableLiveData<M>.toLiveData(): LiveData<M> {
    return this
}

val LiveData<Boolean>.valueOrFalse: Boolean
    get() = this.value ?: false

fun CombinedLoadStates.getErrorState(): LoadState.Error? {
    var errorState: LoadState.Error? = null
    source.forEach { _, loadState ->
        if (loadState is LoadState.Error) {
            errorState = loadState
        }
    }
    return errorState
}

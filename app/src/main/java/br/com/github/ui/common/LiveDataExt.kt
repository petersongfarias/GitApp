package br.com.github.ui.common

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

fun <T> LiveData<T>.observe(owner: LifecycleOwner, onChanged: (T) -> Unit) {
    observe(owner) { onChanged(it) }
}

fun <M> MutableLiveData<M>.toLiveData(): LiveData<M> {
    return this
}

val LiveData<Boolean>.valueOrFalse: Boolean
    get() = this.value ?: false

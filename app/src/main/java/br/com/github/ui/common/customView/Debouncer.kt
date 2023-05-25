package br.com.github.ui.common.customView

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Debouncer(private val debounceMs: Long) {

    private val scope = CoroutineScope(IO)
    private var job: Job? = null

    fun submit(work: () -> Unit) {
        job?.cancel()
        job = scope.launch {
            delay(debounceMs)
            work()
        }
    }

    fun submitSuspendable(work: suspend () -> Unit) {
        job?.cancel()
        job = scope.launch {
            delay(debounceMs)
            work()
        }
    }

    fun cancelLastDebounce() {
        job?.cancel()
    }

    fun shutdown() {
        scope.cancel()
    }
}

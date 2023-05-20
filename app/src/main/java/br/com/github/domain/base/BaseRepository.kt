package br.com.github.domain.base

import br.com.github.utils.coroutines.CoroutineContextProvider
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class BaseRepository : KoinComponent {

    private val contextProvider: CoroutineContextProvider by inject()

    protected suspend fun <T : Any, R> fetchData(
        apiDataProvider: suspend () -> Result<T>
    ): Result<T> =
        withContext(contextProvider.io) {
            apiDataProvider()
        }
}

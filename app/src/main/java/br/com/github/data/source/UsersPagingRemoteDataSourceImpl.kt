package br.com.github.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import br.com.github.data.model.user.UserResponse
import br.com.github.data.service.UserDataService
import timber.log.Timber

class UsersPagingRemoteDataSourceImpl(
    private val service: UserDataService
) : PagingSource<Int, UserResponse>() {

    override val keyReuseSupported: Boolean = true

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserResponse> {
        return try {
            val current = params.key ?: 1

            val response = service.fetchUserList(current, 10)

            LoadResult.Page(
                data = response,
                prevKey = if (current == 1) null else current - 1,
                nextKey = if (response.isEmpty()) null else response.last().id
            )
        } catch (e: Exception) {
            Timber.e("ExamplePagingSource - load() - Exception : " + e.message)
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UserResponse>): Int? {
        return null
    }

    companion object {
        const val TAG = "UserDataPaging"
    }
}

package br.com.github.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import br.com.github.data.model.user.UserResponse
import br.com.github.data.service.UserDataService
import br.com.github.domain.model.user.UserModel
import timber.log.Timber

class UsersRemoteDataSourcePaging(
    private val service: UserDataService
) : PagingSource<Int, UserModel>() {

    override val keyReuseSupported: Boolean = true

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserModel> {
        return try {
            val current = params.key ?: 1

            val users = service.fetchUserList(current, PAGE_LIMIT).map { it.mapTo() }

            LoadResult.Page(
                data = users,
                prevKey = if (current == 1) null else current - 1,
                nextKey = if (users.isEmpty()) null else users.last().id
            )
        } catch (e: Exception) {
            Timber.tag(TAG).e("load() - Exception : ${e.message}" )
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UserModel>): Int? {
        return null
    }

    companion object {
        const val TAG = "UserDataPaging"
        const val PAGE_LIMIT = 10
    }
}

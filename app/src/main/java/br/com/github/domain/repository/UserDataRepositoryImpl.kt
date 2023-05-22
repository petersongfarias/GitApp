package br.com.github.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import br.com.github.data.base.logException
import br.com.github.data.base.mapResult
import br.com.github.data.base.resultOf
import br.com.github.data.source.UserDetailRemoteDataSource
import br.com.github.data.source.UsersRemoteDataSourcePaging
import br.com.github.domain.model.user.UserModel

class UserDataRepositoryImpl(
    private val userDetailRemoteDataSource: UserDetailRemoteDataSource,
    private val usersRemoteDataSourcePaging: UsersRemoteDataSourcePaging
) : UserDataRepository {

    override suspend fun fetchUsers(): Result<Pager<Int, UserModel>> =
        Result.resultOf {
            Pager(
                config = PagingConfig(pageSize = 1),
                pagingSourceFactory = {
                    usersRemoteDataSourcePaging
                }
            )
        }

    override suspend fun fetchUserDetail(userName: String) =
        userDetailRemoteDataSource.fetchUserDetail(userName)
            .mapResult { user ->
                user.mapTo()
            }

    companion object {
        const val TAG = "UserDataRepositoryImpl"
    }
}

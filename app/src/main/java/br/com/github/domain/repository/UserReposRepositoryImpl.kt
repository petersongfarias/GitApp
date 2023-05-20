package br.com.github.domain.repository

import br.com.github.data.base.mapResult
import br.com.github.data.source.UserRepositoryRemoteDataSource
import timber.log.Timber

class UserReposRepositoryImpl(
    private val userRepositoryRemoteDataSource: UserRepositoryRemoteDataSource
) : UserReposRepository {
    override suspend fun fetchRepositoryList(
        userName: String
    ) = userRepositoryRemoteDataSource.fetchRepositoryList(userName)
        .mapResult {
            it.map { repos -> repos.mapTo() }
        }.onFailure {
            Timber.tag(TAG + "fetchRepositoryList").e(it)
        }

    companion object {
        const val TAG = "UserReposRepositoryImpl"
    }
}

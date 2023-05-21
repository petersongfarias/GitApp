package br.com.github.data.service

import br.com.github.data.model.repos.UserRepositoryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserRepositoriesService {

    @GET("users/{userName}/repos")
    suspend fun fetchRepositories(
        @Path("userName")
        userName: String
    ): Response<List<UserRepositoryResponse>>
}

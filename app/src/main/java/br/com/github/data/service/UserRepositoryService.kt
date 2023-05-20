package br.com.github.data.service

import br.com.github.data.model.repos.UserRepositoryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserRepositoryService {

    @GET("users/{userName}/repos")
    suspend fun fetchRepositoryList(
        @Path("userName")
        userName: String
    ): Response<List<UserRepositoryResponse>>
}

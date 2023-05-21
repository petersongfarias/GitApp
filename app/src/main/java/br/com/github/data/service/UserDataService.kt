package br.com.github.data.service

import br.com.github.data.model.user.UserDetailResponse
import br.com.github.data.model.user.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserDataService {

    @GET("users")
    suspend fun fetchUserList(
        @Query("since") since: Int,
        @Query("per_page") perPage: Int
    ): List<UserResponse>

    @GET("users/{userName}")
    suspend fun fetchUser(
        @Path("userName")
        userName: String
    ): Response<UserDetailResponse>
}

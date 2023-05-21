package br.com.github.di

import br.com.github.data.service.UserDataService
import br.com.github.data.service.UserRepositoriesService
import br.com.github.data.source.UserDetailRemoteDataSource
import br.com.github.data.source.UserDetailRemoteDataSourceImpl
import br.com.github.data.source.UserRepositoryRemoteDataSource
import br.com.github.data.source.UserRepositoryRemoteDataSourceImpl
import br.com.github.data.source.UsersRemoteDataSourcePaging
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val TIME_OUT = 30L

val networkModule = module {

    single { createRetrofit(get(), BASE_URL) }

    single { createOkHttpClient() }

    single<UserDataService> { createService(get()) }

    single<UserRepositoriesService> { createService(get()) }

    factory<UserDetailRemoteDataSource> { UserDetailRemoteDataSourceImpl(get()) }

    factory<UserRepositoryRemoteDataSource> { UserRepositoryRemoteDataSourceImpl(get()) }

    factory { UsersRemoteDataSourcePaging(get()) }
}

fun createOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
    val authInterceptor = Interceptor { chain ->
        val req = chain.request()
        val requestHeaders = req.newBuilder()
            .addHeader("Authorization", API_KEY)
            .build()
        chain.proceed(requestHeaders)
    }

    return OkHttpClient.Builder()
        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(TIME_OUT, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor)
        .build()
}

fun createRetrofit(okHttpClient: OkHttpClient, url: String): Retrofit {
    return Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()
}

inline fun <reified T> createService(retrofit: Retrofit): T {
    return retrofit.create(T::class.java)
}

private const val BASE_URL = "https://api.github.com/"
private const val API_KEY =
    "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"

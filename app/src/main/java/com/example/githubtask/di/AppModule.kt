package com.example.githubtask.di

import com.example.githubtask.api.GithubReposService
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single { provideRetrofit("https://api.github.com/") }
    single { provideGithubReposApiService(get()) }
}

private fun provideRetrofit(
    BASE_URL: String
): Retrofit =
    Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

private fun provideGithubReposApiService(retrofit: Retrofit): GithubReposService =
    retrofit.create(GithubReposService::class.java)
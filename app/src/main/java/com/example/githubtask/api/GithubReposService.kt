package com.example.githubtask.api

import com.example.githubtask.models.GithubRepo
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubReposService {

    @GET("/users/JakeWharton/repos")
    suspend fun fetchRepos(
        @Query("page") page: Int,
        @Query("per_page") limit: Int
    ): List<GithubRepo>
}
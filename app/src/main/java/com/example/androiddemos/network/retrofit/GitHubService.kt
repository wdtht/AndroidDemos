package com.example.androiddemos.network.retrofit

import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path

@BaseUrl("https://api.github.com/")
interface GitHubService : RetrofitService {
    @GET("users/{user}/repos")
    fun listRepos(@Path("user") user: String): Flow<List<Repo>>
}
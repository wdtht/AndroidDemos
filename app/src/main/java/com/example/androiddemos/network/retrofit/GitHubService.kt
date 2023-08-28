package com.example.androiddemos.network.retrofit

import kotlinx.coroutines.flow.Flow
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

@CallAdapterFactory(factoryKClass = FlowCallAdapterFactory::class)
@ConvertFactory(factoryKClass = GsonConverterFactory::class)
@BaseUrl("https://api.github.com/")
interface GitHubService {
    @GET("users/{user}/repos")
    fun listRepos(@Path("user") user: String): Flow<List<Repo>>
}
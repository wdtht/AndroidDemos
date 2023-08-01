package com.example.androiddemos.network.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
annotation class BaseUrl(val value: String)

interface RetrofitService

class Api<T : RetrofitService>(private val service: KClass<T>) {

    companion object {
        // 使用 ConcurrentHashMap 来存储 Retrofit 实例
        private val retrofitInstances = ConcurrentHashMap<String, Retrofit>()
    }

    val instance: T by lazy {
        val baseUrl = service.java.getAnnotation(BaseUrl::class.java)?.value
            ?: throw RuntimeException("没有配置baseurl")

        // 使用 computeIfAbsent 方法来添加元素并确保原子性
        val retrofit = retrofitInstances.computeIfAbsent(baseUrl) {
            Retrofit.Builder()
                .baseUrl(it)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        retrofit.create(service.java)
    }
}

inline fun <reified T : RetrofitService> api() = Api(T::class).instance

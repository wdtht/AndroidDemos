package com.example.androiddemos.network.retrofit

import android.util.Log
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class BaseUrl(val value: String)

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class ConvertFactory(val factoryKClass: KClass<out Converter.Factory>)

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class CallAdapterFactory(val factoryKClass: KClass<out CallAdapter.Factory>)

private val retrofitInstances = ConcurrentHashMap<String, Retrofit>()
private val cachedFactories = ConcurrentHashMap<KClass<*>, Any>()

@PublishedApi
internal fun createApi(className: String, annotations: List<Annotation>): Retrofit {
    return retrofitInstances.getOrPut(className) {
        val builder = Retrofit.Builder()

        var hasConvertFactory = false
        var hasCallAdapterFactory = false

        annotations.forEach { annotation ->
            when (annotation) {
                is BaseUrl -> builder.baseUrl(annotation.value)
                is ConvertFactory -> {
                    hasConvertFactory = true
                    val factory = cachedFactories.getOrPut(annotation.factoryKClass) {
                        annotation.factoryKClass.java.getMethod("create").invoke(null)
                    }
                    builder.addConverterFactory(factory as? Converter.Factory ?: throw IllegalArgumentException("Invalid Converter.Factory type"))
                }
                is CallAdapterFactory -> {
                    hasCallAdapterFactory = true
                    val factory = cachedFactories.getOrPut(annotation.factoryKClass) {
                        annotation.factoryKClass.java.getMethod("create").invoke(null)
                    }
                    builder.addCallAdapterFactory(factory as? CallAdapter.Factory ?: throw IllegalArgumentException("Invalid CallAdapter.Factory type"))
                }
                else -> {}
            }
        }

        // 如果没有添加 ConvertFactory 注解，使用默认的 GsonConverterFactory
        if (!hasConvertFactory) {
            builder.addConverterFactory(GsonConverterFactory.create())
        }

        // 如果没有添加 CallAdapterFactory 注解，使用默认的 FlowCallAdapterFactory
        if (!hasCallAdapterFactory) {
            builder.addCallAdapterFactory(FlowCallAdapterFactory.create())
        }

        builder.build()
    }
}

inline fun <reified T> api(): T {
    val className = T::class.java.name
    val annotations = T::class.java.annotations.toList()
    val retrofit = createApi(className, annotations)
    return retrofit.create(T::class.java)
}

class FlowCallAdapterFactory private constructor() : CallAdapter.Factory() {
    companion object {
        @JvmStatic
        fun create() = FlowCallAdapterFactory()
    }

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != Flow::class.java) {
            return null
        }
        require(returnType is ParameterizedType) { "Flow return type must be parameterized as Flow<Foo> or Flow<out Foo>" }

        val responseType = getParameterUpperBound(0, returnType)
        val apiName = returnType.toString()
        return FlowCallAdapter<Any>(responseType, apiName)
    }

    private class FlowCallAdapter<OriginResult>(
        private val responseType: Type,
        private val apiName: String
    ) : CallAdapter<OriginResult, Flow<OriginResult>> {
        override fun responseType(): Type = responseType

        override fun adapt(call: Call<OriginResult>): Flow<OriginResult> = callbackFlow {
            val callback = object : Callback<OriginResult> {
                override fun onFailure(call: Call<OriginResult>, t: Throwable) {
                    val errorMsg = "Request failed for API $apiName: ${t.message}"
                    Log.e("FlowCallAdapter", errorMsg, t)
                    close(Throwable(errorMsg, t))
                }

                override fun onResponse(call: Call<OriginResult>, response: Response<OriginResult>) {
                    if (response.isSuccessful) {
                        Log.d("FlowCallAdapter", "Request successful for API $apiName")
                        response.body()?.let {
                            Log.d("FlowCallAdapter", "Response body type for API $apiName: ${it.javaClass}")
                            trySend(it)
                        } ?: close(Throwable("Response body is null for API $apiName"))
                    } else {
                        val errorMsg = "Request not successful for API $apiName: ${response.code()}"
                        Log.e("FlowCallAdapter", errorMsg)
                        close(Throwable(errorMsg))
                    }
                }
            }
            call.enqueue(callback)
            awaitClose { call.cancel() }
        }.catch { it.printStackTrace() }
    }
}

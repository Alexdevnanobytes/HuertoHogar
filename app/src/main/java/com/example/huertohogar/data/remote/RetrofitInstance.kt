package com.example.huertohogar.data.remote

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    // 🔐 Token en memoria (se puede mejorar guardándolo en DataStore)
    var jwtToken: String? = null

    // 🟦 Interceptor para agregar el JWT automáticamente
    private val authInterceptor = Interceptor { chain ->
        val requestBuilder = chain.request().newBuilder()

        jwtToken?.let { token ->
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }

        chain.proceed(requestBuilder.build())
    }

    // 🟧 Interceptor para logs y debugging
    private val loggingInterceptor = Interceptor { chain ->
        val request = chain.request()
        val response: Response = try {
            chain.proceed(request)
        } catch (e: Exception) {
            println("🚨 Error en Retrofit: ${e.message}")
            throw e
        }

        println("📡 Request → ${request.method} ${request.url}")
        println("➡ Headers: ${request.headers}")
        println("⬅ Response Code: ${response.code}")

        response
    }

    // 🟩 Cliente HTTP final
    private val client = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)     // agrega JWT si existe
        .addInterceptor(loggingInterceptor)  // logs
        .build()

    // 🟦 Retrofit configurado
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}

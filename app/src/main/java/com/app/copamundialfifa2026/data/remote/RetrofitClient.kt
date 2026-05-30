package com.app.copamundialfifa2026.data.remote

import com.app.copamundialfifa2026.core.AppConstants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Retrofit setup, ready for a real backend. It is fully wired (base URL, OkHttp logging, Gson) so
 * that swapping the repositories from mock to live only requires calling these services.
 */
object RetrofitClient {

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(AppConstants.Api.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val ticketApiService: TicketApiService by lazy { retrofit.create(TicketApiService::class.java) }
    val authApiService: AuthApiService by lazy { retrofit.create(AuthApiService::class.java) }
}

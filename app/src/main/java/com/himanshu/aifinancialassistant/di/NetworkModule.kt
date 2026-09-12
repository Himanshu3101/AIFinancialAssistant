package com.himanshu.aifinancialassistant.di

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.himanshu.aifinancialassistant.data.remote.AIService
import com.himanshu.aifinancialassistant.data.remote.AuthInterceptor
import com.himanshu.aifinancialassistant.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://generativelanguage.googleapis.com/")
            .client(okHttpClient)
            .addConverterFactory(
                Json{
                    ignoreUnknownKeys = true
                }.asConverterFactory(
                    "application/json".toMediaType()
                )
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideApiKey(): String{
        return BuildConfig.GEMINI_API_KEY
    }

    @Provides
    @Singleton
    fun provideAIService(
        retrofit: Retrofit
    ): AIService{
        return retrofit.create(AIService::class.java)
    }
}
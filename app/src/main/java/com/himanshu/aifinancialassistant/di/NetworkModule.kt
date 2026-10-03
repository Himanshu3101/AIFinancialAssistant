package com.himanshu.aifinancialassistant.di

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.himanshu.aifinancialassistant.data.remote.AIService
import com.himanshu.aifinancialassistant.data.remote.AuthInterceptor
import com.himanshu.aifinancialassistant.BuildConfig
import com.himanshu.aifinancialassistant.data.remote.ollama.OllamaService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
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
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
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
            .baseUrl("http://192.168.1.44:11434/")
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

    @Provides
    @Singleton
    fun provideOllamaService(
        retrofit: Retrofit
    ): OllamaService{
        return retrofit.create(OllamaService::class.java)
    }
}
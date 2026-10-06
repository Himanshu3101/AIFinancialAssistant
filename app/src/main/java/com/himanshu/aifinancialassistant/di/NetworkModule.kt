package com.himanshu.aifinancialassistant.di

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.himanshu.aifinancialassistant.data.remote.gemini.GeminiService
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
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    // ---------------------------------------------------------
    // Logging
    // ---------------------------------------------------------
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
            redactHeader("x-goog-api-key")
        }
    }

    // ---------------------------------------------------------
    // Gemini OkHttp
    // ---------------------------------------------------------
    @Provides
    @Singleton
    @Named("Gemini")
    fun provideGeminiOkHttpClient(
        authInterceptor: AuthInterceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    // ---------------------------------------------------------
    // Ollama OkHttp
    // ---------------------------------------------------------
    @Provides
    @Singleton
    @Named("Ollama")
    fun provideOllamaOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    // ---------------------------------------------------------
    // Common JSON
    // ---------------------------------------------------------
    @Provides
    @Singleton
    fun provideJson(): Json{
        return Json {
            ignoreUnknownKeys = true
        }
    }

    // ---------------------------------------------------------
    // Gemini Retrofit
    // ---------------------------------------------------------
    @Provides
    @Singleton
    @Named("Gemini")
    fun provideGeminiRetrofit(
        @Named("Gemini") okHttpClient: OkHttpClient,
        json: Json
    ): Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://generativelanguage.googleapis.com/")
            .client(okHttpClient)
            .addConverterFactory(
                json.asConverterFactory(
                    "application/json".toMediaType()
                )
            )
            .build()
    }

    // ---------------------------------------------------------
    // Ollama Retrofit
    // ---------------------------------------------------------
    @Provides
    @Singleton
    @Named("Ollama")
    fun provideOllamaRetrofit(
        @Named("Ollama") okHttpClient: OkHttpClient,
        json: Json
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl ("http://192.168.1.44:11434/")
            .client(okHttpClient)
            .addConverterFactory(
                json.asConverterFactory(
                    "application/json".toMediaType()
                )
            )
            .build()
    }

    // ---------------------------------------------------------
    // Gemini API Key
    // ---------------------------------------------------------
    @Provides
    @Singleton
    fun provideGeminiApiKey(): String{
        return BuildConfig.GEMINI_API_KEY
    }

    // ---------------------------------------------------------
    // Gemini Service
    // ---------------------------------------------------------
    @Provides
    @Singleton
    fun provideGeminiService(
        @Named("Gemini") retrofit: Retrofit
    ): GeminiService{
        return retrofit.create(GeminiService::class.java)
    }

    // ---------------------------------------------------------
    // Ollama Service
    // ---------------------------------------------------------
    @Provides
    @Singleton
    fun provideOllamaService(
        @Named("Ollama") retrofit: Retrofit
    ): OllamaService{
        return retrofit.create(OllamaService::class.java)
    }
}
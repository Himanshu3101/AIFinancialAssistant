package com.himanshu.aifinancialassistant.di

import com.himanshu.aifinancialassistant.data.remote.ollama.OllamaProvider
import com.himanshu.aifinancialassistant.domain.ai.AIProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AIProviderModule {

    @Provides
    @Singleton
    fun provideAIProvider(
        ollamaProvider: OllamaProvider
    ): AIProvider{
        return ollamaProvider
    }
}
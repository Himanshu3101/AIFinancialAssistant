package com.himanshu.aifinancialassistant.di

import com.himanshu.aifinancialassistant.BuildConfig
import com.himanshu.aifinancialassistant.data.remote.gemini.GeminiProvider
import com.himanshu.aifinancialassistant.data.remote.ollama.OllamaProvider
import com.himanshu.aifinancialassistant.domain.ai.AIProvider
import com.himanshu.aifinancialassistant.domain.ai.AIProviderType
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
        geminiProvider: GeminiProvider,
        ollamaProvider: OllamaProvider
    ): AIProvider{
        return when (AIProviderType.valueOf(BuildConfig.AI_PROVIDER)){
            AIProviderType.OLLAMA -> ollamaProvider
            AIProviderType.GEMINI -> geminiProvider
        }
    }
}
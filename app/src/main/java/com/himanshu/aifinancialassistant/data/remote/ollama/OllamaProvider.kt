package com.himanshu.aifinancialassistant.data.remote.ollama

import android.util.Log
import com.himanshu.aifinancialassistant.domain.ai.AIProvider
import com.himanshu.aifinancialassistant.domain.ai.AIRequest
import com.himanshu.aifinancialassistant.domain.ai.AIResult
import javax.inject.Inject

class OllamaProvider @Inject constructor(
    private val ollamaService: OllamaService
): AIProvider {
    override suspend fun generate(request: AIRequest): AIResult {

        val ollamaRequest = request.toOllamaRequest()

        val response = ollamaService.chat(ollamaRequest)

        return response.toAIResult()
    }
}
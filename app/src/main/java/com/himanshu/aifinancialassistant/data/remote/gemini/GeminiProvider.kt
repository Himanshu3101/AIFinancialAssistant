package com.himanshu.aifinancialassistant.data.remote.gemini

import com.himanshu.aifinancialassistant.domain.ai.AIProvider
import com.himanshu.aifinancialassistant.domain.ai.AIRequest
import com.himanshu.aifinancialassistant.domain.ai.AIResult
import javax.inject.Inject

class GeminiProvider @Inject constructor(
    private val geminiService: GeminiService
): AIProvider {
    override suspend fun generate(request: AIRequest): AIResult {
        val geminiRequest = request.toGeminiRequest()

        val response = geminiService.generateResponse(
            geminiRequest
        )

        return response.toAIResult()
    }
}

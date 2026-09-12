package com.himanshu.aifinancialassistant.data.remote

import com.himanshu.aifinancialassistant.data.remote.model.AIRequest
import com.himanshu.aifinancialassistant.data.remote.model.AIResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AIService{
    @POST("v1beta/models/gemini-3.7-flash:generateContent")
    suspend fun generateResponse(
        @Body request: AIRequest
    ): AIResponse
}
package com.himanshu.aifinancialassistant.data.remote.gemini

import retrofit2.http.Body
import retrofit2.http.POST

interface GeminiService{
    @POST("v1beta/models/gemini-3.7-flash:generateContent")
    suspend fun generateResponse(
        @Body request: GeminiModelRequest
    ): GeminiModelResponse
}
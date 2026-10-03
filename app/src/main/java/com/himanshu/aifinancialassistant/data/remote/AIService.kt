package com.himanshu.aifinancialassistant.data.remote

import com.himanshu.aifinancialassistant.data.remote.model.gemini.GeminiModelReqest
import com.himanshu.aifinancialassistant.data.remote.model.gemini.GeminiModelResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AIService{
//    @POST("v1beta/models/gemini-3.7-flash:generateContent")
    @POST("api/chat")
    suspend fun generateResponse(
        @Body request: GeminiModelReqest
    ): GeminiModelResponse
}
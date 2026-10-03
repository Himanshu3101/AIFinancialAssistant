package com.himanshu.aifinancialassistant.data.remote.ollama

import retrofit2.http.Body
import retrofit2.http.POST

interface OllamaService{

    @POST("api/chat")
    suspend fun chat(
        @Body request: OllamaRequest
    ): OllamaResponse
}
package com.himanshu.aifinancialassistant.domain.ai

interface AIProvider{

//AIProvider
//    = talks to the LLM

    suspend fun generate(
        request: AIRequest
    ): AIResult

}
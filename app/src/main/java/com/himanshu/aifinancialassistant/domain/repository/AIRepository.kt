package com.himanshu.aifinancialassistant.domain.repository

interface AIRepository{

    suspend fun askFinancialAssistant(
        systemPrompt: String,
        context: String,
        userPrompt:String
    ): String
}
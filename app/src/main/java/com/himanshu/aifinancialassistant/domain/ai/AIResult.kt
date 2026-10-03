package com.himanshu.aifinancialassistant.domain.ai


//What the AI provider returned to our application
sealed interface AIResult {

    data class Text(
        val content: String,
    ): AIResult

    data class ToolCalls(
        val calls: List<AIToolCall>
    ): AIResult
}
package com.himanshu.aifinancialassistant.data.remote.ollama

import kotlinx.serialization.Serializable

@Serializable
data class OllamaResponse(
    val model: String? = null,
    val message: OllamaResponseMessage,
    val done: Boolean = false
)

@Serializable
data class OllamaResponseMessage(
        val role: String,
    val content: String = "",
    val tool_calls:List<OllamaToolCall> = emptyList()
)
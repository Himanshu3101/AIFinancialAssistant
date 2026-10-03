package com.himanshu.aifinancialassistant.data.remote.ollama

import kotlinx.serialization.Serializable

@Serializable
data class OllamaRequest(
    val model: String,
    val messages: List<OllamaMessage>,
    val tools: List<OllamaTool> = emptyList(),
    val stream: Boolean
)

@Serializable
data class OllamaMessage(
    val role: String,
    val content: String = "",
    val tool_calls: List<OllamaToolCall> = emptyList(),
    val tool_name: String? = null
)
@Serializable
data class OllamaTool(
    val type: String = "function",
    val function: OllamaFunction
)
@Serializable
data class OllamaFunction(
    val name: String,
    val description: String,
    val parameters: OllamaParameters
)
@Serializable
data class OllamaParameters(
    val type:String = "object",
    val properties: Map<String, OllamaProperty>,
    val required: List<String> = emptyList()
)
@Serializable
data class OllamaProperty(
    val type: String,
    val description: String? = null,
    val enum:List<String>? = null
)
@Serializable
data class OllamaToolCall(
    val function: OllamaCalledFunction
)
@Serializable
data class OllamaCalledFunction(
    val name:String,
    val arguments: Map<String, String> = emptyMap()
)
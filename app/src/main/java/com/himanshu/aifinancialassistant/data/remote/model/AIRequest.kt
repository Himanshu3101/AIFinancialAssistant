package com.himanshu.aifinancialassistant.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class AIRequest(
    val systemInstruction: SystemInstruction? = null,  //Gemini Support SystemInstruction alongside contentsA
    val contents: List<Content>,
    val tools: List<Tool>? = null,
    val toolConfig: ToolConfig? = null
)

@Serializable
data class ToolConfig(
    val functionCallingConfig: FunctionCallingConfig
)

@Serializable
data class FunctionCallingConfig(
    val mode: String = "AUTO"
)

@Serializable
data class Content(
    val role: String,
    val parts: List<Part>
)

@Serializable
data class Part(
    val text: String
)

@Serializable
data class SystemInstruction(
    val parts: List<Part>
)
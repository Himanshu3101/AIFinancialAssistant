package com.himanshu.aifinancialassistant.data.remote.gemini

import com.himanshu.aifinancialassistant.data.remote.model.Tool
import kotlinx.serialization.Serializable

@Serializable
data class GeminiModelRequest(
    val systemInstruction: SystemInstruction? = null,  //Gemini Support SystemInstruction alongside contentsA
    val contents: List<GeminiContent>,
    val tools: List<Tool>? = null,
    val toolConfig: ToolConfig? = null
)

@Serializable
data class SystemInstruction(
    val parts: List<Part>
)

@Serializable
data class GeminiContent(
    val role: String,
    val parts: List<Part>
)

@Serializable
data class Part(
    val text: String? = null,
    val functionCall: FunctionCall?  = null,
    val functionResponse: FunctionResponse? = null,
    val thoughtSignature: String? = null
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
data class FunctionResponse(
    val id: String,
    val name: String,
    val response:Map<String, String>
)
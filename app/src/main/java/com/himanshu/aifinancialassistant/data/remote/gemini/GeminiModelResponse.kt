package com.himanshu.aifinancialassistant.data.remote.gemini

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class GeminiModelResponse(
    val candidates: List<Candidate> = emptyList()
)
@Serializable
data class Candidate(
    val content: ResponseContent? = null
)
@Serializable
data class ResponseContent(
    val parts: List<ResponsePart> = emptyList(),
    val role: String? = null
)
@Serializable
data class ResponsePart(
    val text: String? = null,
    val functionCall: FunctionCall? = null,
    val thoughtSignature: String? = null
)

@Serializable
data class FunctionCall(
    val id: String? = null,
    val name: String,
    val args: Map<String, JsonElement> = emptyMap()
)
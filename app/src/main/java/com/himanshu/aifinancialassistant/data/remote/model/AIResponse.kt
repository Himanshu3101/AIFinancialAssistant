package com.himanshu.aifinancialassistant.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class AIResponse(
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
    val functionCall: FunctionCall? = null
)

@Serializable
data class FunctionCall(
    val id: String? = null,
    val name: String,
    val args: Map<String, kotlinx.serialization.json.JsonElement> = emptyMap()
)
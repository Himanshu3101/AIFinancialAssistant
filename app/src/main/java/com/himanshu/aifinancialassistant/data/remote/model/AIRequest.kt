package com.himanshu.aifinancialassistant.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class AIRequest(
    val systemInstruction: SystemInstruction? = null,  //Gemini Support SystemInstruction alongside contentsA
    val contents: List<Content>
)

@Serializable
data class Content(
    val role: String = "user",
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
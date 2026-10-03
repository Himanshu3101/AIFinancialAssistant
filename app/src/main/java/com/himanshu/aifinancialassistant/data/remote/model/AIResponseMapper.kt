package com.himanshu.aifinancialassistant.data.remote.model

import com.himanshu.aifinancialassistant.data.remote.model.gemini.GeminiModelResponse

fun GeminiModelResponse.toText(): String{
    return candidates
        .flatMap { it.content?.parts.orEmpty() }
        .mapNotNull { it.text }
        .joinToString("\n")
}
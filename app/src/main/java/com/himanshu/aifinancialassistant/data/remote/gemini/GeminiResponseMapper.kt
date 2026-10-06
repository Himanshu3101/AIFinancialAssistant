package com.himanshu.aifinancialassistant.data.remote.gemini

import com.himanshu.aifinancialassistant.domain.ai.AIResult
import com.himanshu.aifinancialassistant.domain.ai.AIToolCall
import kotlinx.serialization.json.jsonPrimitive

fun GeminiModelResponse.toAIResult(): AIResult {

    val parts = candidates
        .flatMap { it.content?.parts.orEmpty() }

    val toolCalls = parts
        .mapNotNull { part ->
            part.functionCall?.let { functionCall->

                AIToolCall(
                    id = functionCall.id ?: "gemini_call_${functionCall.name}",
                    name = functionCall.name,
                    arguments = functionCall.args.mapValues { (_, value) ->
                        value.jsonPrimitive.content
                    },
                    thoughtSignature = part.thoughtSignature
                )
            }
        }
    if(toolCalls.isNotEmpty()){
        return AIResult.ToolCalls(toolCalls)
    }

    val text = parts
        .mapNotNull { it. text }
        .joinToString ("\n")
    return AIResult.Text(text)
}
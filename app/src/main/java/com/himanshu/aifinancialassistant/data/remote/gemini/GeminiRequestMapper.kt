package com.himanshu.aifinancialassistant.data.remote.gemini

import com.himanshu.aifinancialassistant.data.remote.model.FunctionParameters
import com.himanshu.aifinancialassistant.data.remote.model.GeminiFunctionDeclaration
import com.himanshu.aifinancialassistant.data.remote.model.PropertyDefinition
import com.himanshu.aifinancialassistant.data.remote.model.Tool
import com.himanshu.aifinancialassistant.domain.ai.AIMessage
import com.himanshu.aifinancialassistant.domain.ai.AIRequest
import com.himanshu.aifinancialassistant.domain.ai.AIRole
import com.himanshu.aifinancialassistant.domain.ai.AITool
import kotlinx.serialization.json.JsonPrimitive

fun AIRequest.toGeminiRequest(): GeminiModelRequest{

    val content = messages.map { it.toGeminiContent() }

    val tools = tools
        .takeIf { it.isNotEmpty() }
        ?.let { tools ->
            listOf(
                Tool(
                    functionDeclarations = tools.map {it.toGeminiFunctionDeclaration()}
                )
            )
        }

    return GeminiModelRequest(
        systemInstruction = systemPrompt
            ?.takeIf { it.isNotBlank() }
            ?.let {
                SystemInstruction(
                    parts = listOf(
                        Part(text = it)
                    )
                )
            },
        contents = content,
        tools = tools,
        toolConfig = null
    )
}

fun AIMessage.toGeminiContent(): GeminiContent {
    return when(role){
        AIRole.USER -> {
            GeminiContent(
                role = "user",
                parts = listOf(
                    Part(text = content.orEmpty())
                )
            )
        }
        AIRole.ASSISTANT -> {
            GeminiContent(
                role = "model",
                parts = toolCall.map { call->
                    Part(
                        functionCall = FunctionCall(
                            id = call.id,
                            name = call.name,
                            args = call.arguments.mapValues {
                                JsonPrimitive(it.value)
                            }
                        ),
                        thoughtSignature = call.thoughtSignature
                    )
                }.ifEmpty {
                    listOf(
                        Part(text = content.orEmpty())
                    )
                }
            )
        }
        AIRole.TOOL -> {
            val result = toolResult
                ?: return GeminiContent(
                    role = "user",
                    parts = emptyList()
                )

            GeminiContent(
                role = "user",
                parts = listOf(
                    Part(
                        functionResponse = FunctionResponse(
                            id = result.id,
                            name = result.name,
                            response = mapOf(
                                "result" to result.result
                            )
                        )
                    )
                )
            )
        }
    }
}

fun AITool.toGeminiFunctionDeclaration(): GeminiFunctionDeclaration {
    return GeminiFunctionDeclaration(
        name = name,
        description = description,
        parameters = FunctionParameters(
            type = "OBJECT",
            description = null,
            properties = parameters.mapValues {  (_, value) ->
                PropertyDefinition(
                    type = value.type.uppercase(),
                    description = value.description,
                    enum = value.enumValues
                )
            },
            required = requiredParameter
        )
    )
}
package com.himanshu.aifinancialassistant.data.remote.ollama

import com.himanshu.aifinancialassistant.domain.ai.AIMessage
import com.himanshu.aifinancialassistant.domain.ai.AIRequest
import com.himanshu.aifinancialassistant.domain.ai.AIRole
import com.himanshu.aifinancialassistant.domain.ai.AITool

fun AIRequest.toOllamaRequest(
    model: String = "qwen3:1.7b"
): OllamaRequest {
    val messages = buildList {
        systemPrompt
            ?.takeIf { it.isNotBlank() }
            ?.let { prompt ->
                add(
                    OllamaMessage(
                        role = "system",
                        content = prompt
                    )
                )
            }
        messages.forEach { aiMessage ->
            add(aiMessage.toOllamaMessage())
        }
    }
    return OllamaRequest(
        model = model,
        messages = messages,
        tools = tools.map {it.toOllamaTool()},
        stream = false
    )
}


// add the message mapper:

private fun AIMessage.toOllamaMessage(): OllamaMessage {
    return when(role){
        AIRole.USER -> {
            OllamaMessage(
                role = "user",
                content = content.orEmpty()
            )
        }
        AIRole.ASSISTANT -> {
            OllamaMessage(
                role = "assistant",
                content = content.orEmpty(),
                tool_calls = toolCall.map {
                    OllamaToolCall(
                        function = OllamaCalledFunction(
                            name = it.name,
                            arguments = it.arguments
                        )
                    )
                }
            )
        }
        AIRole.TOOL -> {
            val result = toolResult

            OllamaMessage(
                role = "tool",
                content = result?.result.orEmpty(),
                tool_name = result?.name
            )
        }
    }
}


//tool mapper:
private fun AITool.toOllamaTool(): OllamaTool{
    return OllamaTool(
        type = "function",
        function = OllamaFunction(
            name = name,
            description = description,
            parameters = OllamaParameters(
                type = "object",
                properties = parameters.mapValues { (_, value) ->
                    OllamaProperty(
                        type = value.type,
                        description = value.description,
                        enum = value.enumValues
                    )
                },
                required = requiredParameter
            )
        )
    )
}

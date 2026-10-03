package com.himanshu.aifinancialassistant.data.remote.ollama

import com.himanshu.aifinancialassistant.domain.ai.AIResult
import com.himanshu.aifinancialassistant.domain.ai.AIToolCall

fun OllamaResponse.toAIResult(): AIResult{

    val toolCalls = message.tool_calls

    if(toolCalls.isNotEmpty()){
        return AIResult.ToolCalls(
            calls = toolCalls.mapIndexed { index, toolCall  ->

                AIToolCall(
                    id = "ollema_call_$index",
                    name = toolCall .function.name,
                    arguments = toolCall .function.arguments
                )
            }
        )
    }
    return AIResult.Text(
        content = message.content
    )
}
package com.himanshu.aifinancialassistant.domain.ai

data class AIRequest(
    val systemPrompt: String?,
    val messages: List<AIMessage>,
    val tools : List<AITool> = emptyList()
)

data class AIMessage(
    val role: AIRole,
    val content: String? = null,
    val toolCall: List<AIToolCall> = emptyList(),
    val toolResult: AIToolResult? = null
)

enum class AIRole{
    USER,
    ASSISTANT,
    TOOL
}

data class AIToolCall(
    val id: String,
    val name: String,
    val arguments: Map<String, String/*ToolArgument*/>
)
data class AITool(
    val name: String,
    val description: String,
    val parameters: Map<String, AIToolParameter>,
    val requiredParameter: List<String>
)

data class AIToolParameter(
    val type: String,
    val description: String?,
    val enumValues: List<String>?
)







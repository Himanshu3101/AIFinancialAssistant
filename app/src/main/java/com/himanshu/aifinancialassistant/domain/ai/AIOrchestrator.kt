package com.himanshu.aifinancialassistant.domain.ai

import android.util.Log
import com.himanshu.aifinancialassistant.domain.tool.FinancialToolRegistry
import javax.inject.Inject

//This will be provider-independent: - AIOrchestrator
//     manages the conversation/tool workflow

class AIOrchestrator @Inject constructor(
    private val aiProvider: AIProvider,   // OllamaProvider, GeminiProvider or ClaudeProvider
    private val financialToolRegistry: FinancialToolRegistry     // getSpendingByCategory -> GetSpendingByCategoryTool
//    orchestrator can execute whichever tool the AI requests.
) {

    suspend fun execute(
        request: AIRequest
    ): String {

        var currentRequest = request.copy(
            tools = financialToolRegistry.getToolDefinitions()
        )

        //is simply a safety guard. |  It's not saying the AI should always call three times. | It could finish in one round.  |  Allow at most 3 AI/tool rounds.
        repeat(3) {

            when(val result = aiProvider.generate(currentRequest)){
                is AIResult.Text ->{
                    return result.content
                }

                is AIResult.ToolCalls -> {

                    //assuming only one tool, we're preparing a list.
                    val toolMessages = mutableListOf<AIMessage>()

                    //Find the correct tool
                    result.calls.forEach { call ->
                        val tool = financialToolRegistry.getTool(call.name) ?: throw IllegalArgumentException(
                                "Unknown tool: ${call.name}"
                            )


                        //Execute the tool
                        val toolResult = tool.execute(
                            call.arguments
                        )

                        toolMessages.add(
                            AIMessage(
                                role = AIRole.TOOL,
                                toolResult = AIToolResult(
                                    id = call.id,
                                    name = call.name,
                                    result = toolResult
                                )
                            )
                        )
                    }

                    //"Take the old conversation and append what just happened
                    currentRequest = currentRequest.copy(
                        messages = currentRequest.messages+
                                AIMessage(
                                    role = AIRole.ASSISTANT,
                                    toolCall = result.calls
                                ) +
                        toolMessages
                    )
                }
            }
        }

        throw IllegalStateException(
            "AI tool execution exceeded maximum rounds."
        )
    }
}



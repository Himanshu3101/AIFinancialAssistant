package com.himanshu.aifinancialassistant.data.repositoryImpl

import com.himanshu.aifinancialassistant.data.remote.AIService
import com.himanshu.aifinancialassistant.data.remote.GeminiFinancialTools
import com.himanshu.aifinancialassistant.data.remote.model.AIRequest
import com.himanshu.aifinancialassistant.data.remote.model.Content
import com.himanshu.aifinancialassistant.data.remote.model.FunctionCallingConfig
import com.himanshu.aifinancialassistant.data.remote.model.Part
import com.himanshu.aifinancialassistant.data.remote.model.SystemInstruction
import com.himanshu.aifinancialassistant.data.remote.model.ToolConfig
import com.himanshu.aifinancialassistant.data.remote.model.toText
import com.himanshu.aifinancialassistant.data.remote.toToolArguments
import com.himanshu.aifinancialassistant.domain.repository.AIRepository
import com.himanshu.aifinancialassistant.domain.tool.FinancialToolRegistry
import javax.inject.Inject

class AIRepositoryImpl @Inject constructor(
    private val aiService: AIService,
    private val financialToolRegistry: FinancialToolRegistry
) : AIRepository {

    override suspend fun askFinancialAssistant(
        systemPrompt: String,
        context: String,
        userPrompt: String
    ): String {
        val request = AIRequest(
            systemInstruction = SystemInstruction(
                parts = listOf(
                    Part(text = systemPrompt)
                )
            ),
            contents = listOf(
                Content(
                    role = "user",
                    parts = listOf(
                        Part(
                            text = userPrompt
                            /*text = """
                            Financial Context:
                            $context

                            User Question:
                            $userPrompt
                        """.trimIndent()*/
                        )
                    )
                )
            ),
            tools = listOf(
                GeminiFinancialTools.getSpendingByCategory
            ),
            toolConfig = ToolConfig(
                functionCallingConfig = FunctionCallingConfig(
                    mode = "ANY"
                )
            )
        )

        val response = aiService.generateResponse(request)

        val functionCall = response.candidates
            .flatMap { it.content?.parts.orEmpty() }
            .firstNotNullOfOrNull { it.functionCall }

        if(functionCall != null){

            val tool = financialToolRegistry.getTool(functionCall.name)?: throw IllegalArgumentException(
                "Unknow tool: ${functionCall.name}"
            )

            val arguments = functionCall.toToolArguments()

            val toolResult = tool.execute(arguments)

        }









        val result = response.toText()
        if(result.isBlank()){
            throw IllegalArgumentException("AI returned an empty response")
        }
        return response.toText()
    }
}
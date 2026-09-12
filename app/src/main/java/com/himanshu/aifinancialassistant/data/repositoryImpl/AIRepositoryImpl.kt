package com.himanshu.aifinancialassistant.data.repositoryImpl

import com.himanshu.aifinancialassistant.data.remote.AIService
import com.himanshu.aifinancialassistant.data.remote.model.AIRequest
import com.himanshu.aifinancialassistant.data.remote.model.Content
import com.himanshu.aifinancialassistant.data.remote.model.Part
import com.himanshu.aifinancialassistant.data.remote.model.SystemInstruction
import com.himanshu.aifinancialassistant.data.remote.model.toText
import com.himanshu.aifinancialassistant.domain.repository.AIRepository
import javax.inject.Inject

class AIRepositoryImpl @Inject constructor(
    private val aiService: AIService
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
                            text = """
                            Financial Context:
                            $context

                            User Question:
                            $userPrompt
                        """.trimIndent()
                        )
                    )
                )
            )
        )

        val response = aiService.generateResponse(request)

        val result = response.toText()
        if(result.isBlank()){
            throw IllegalArgumentException("AI returned an empty response")
        }
        return response.toText()
    }
}
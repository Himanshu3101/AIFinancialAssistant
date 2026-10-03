package com.himanshu.aifinancialassistant.domain.usecase

import com.himanshu.aifinancialassistant.data.remote.FinancialContextFormatter
import com.himanshu.aifinancialassistant.domain.ai.AIMessage
import com.himanshu.aifinancialassistant.domain.ai.AIOrchestrator
import com.himanshu.aifinancialassistant.domain.ai.AIRequest
import com.himanshu.aifinancialassistant.domain.ai.AIRole
import javax.inject.Inject

class AskFinancialAssistantUseCase @Inject constructor(
    private val aiOrchestrator: AIOrchestrator,
    private val classifyFinancialQuestionUseCase: ClassifyFinancialQuestionUseCase,
    private val buildFinancialContextUseCase: BuildFinancialContextUseCase,
    private val financialContextFormatter: FinancialContextFormatter
) {
    suspend operator fun invoke(
        userPrompt: String
    ): String{

        val intent =  classifyFinancialQuestionUseCase(userPrompt)

        val financialContext = buildFinancialContextUseCase(intent)

        val context = financialContextFormatter.format(
            financialContext
        )

        val systemPrompt = """
             You are a personal financial assistant.

            Use the available financial tools to retrieve financial information.

            Do not invent financial facts or transactions.
            
             Keep your response concise and easy to understand.
        """.trimIndent()

        val request = AIRequest(
            systemPrompt = systemPrompt,
            messages = listOf(
                AIMessage(
                    role = AIRole.USER,
                    content = """
                        Financial Context:
                        $context
                        
                        User Question:
                        $userPrompt
                        """.trimIndent()
                )
            )
        )
        return aiOrchestrator.execute(request)
    }
}
package com.himanshu.aifinancialassistant.domain.usecase

import com.himanshu.aifinancialassistant.data.remote.FinancialContextFormatter
import com.himanshu.aifinancialassistant.domain.repository.AIRepository
import javax.inject.Inject

class AskFinancialAssistantUseCase @Inject constructor(
    private val aiRepository: AIRepository,
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

            Use only the financial information provided.

            Do not invent financial facts or transactions.

            If the provided information is insufficient,
            clearly tell the user that you don't have enough information.

            Keep your response concise and easy to understand.
        """.trimIndent()

        return aiRepository.askFinancialAssistant(
            systemPrompt,
            context,
            userPrompt
        )
    }
}
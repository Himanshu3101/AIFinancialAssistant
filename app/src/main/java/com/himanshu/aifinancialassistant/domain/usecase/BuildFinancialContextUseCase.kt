package com.himanshu.aifinancialassistant.domain.usecase

import com.himanshu.aifinancialassistant.domain.model.FinancialContext
import com.himanshu.aifinancialassistant.domain.model.FinancialQuestionIntent
import com.himanshu.aifinancialassistant.domain.model.FinancialQuestionType
import com.himanshu.aifinancialassistant.domain.model.TransactionType
import com.himanshu.aifinancialassistant.domain.repository.FinancialRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class BuildFinancialContextUseCase @Inject constructor(
    private val getFinancialSummaryUseCase: GetFinancialSummaryUseCase
) {
    suspend operator fun invoke(
        questionType: FinancialQuestionIntent
    ): FinancialContext {

        val summary = getFinancialSummaryUseCase()

        val relevantInformation = when (questionType.type) {

            FinancialQuestionType.TOTAL_SPENDING -> {
                "Total Spending: Rs. ${summary.totalSpent}"
            }

            FinancialQuestionType.CATEGORY_SPENDING -> {
                val category = questionType.category
                if (category != null) {
                    val amount = summary.spendingByCategory[category] ?: 0.0
                    "${category.name}: ₹$amount"
                }else{
                    null
                }
            }

            FinancialQuestionType.TOP_CATEGORY -> {
                val topCategory = summary.spendingByCategory.maxByOrNull { it.value }

                topCategory?.let{
                    "Top Spending category: ${it.key.name}, amount: Rs. ${it.value}"
                }
            }

            FinancialQuestionType.GENERAL ->  null
        }

        return FinancialContext(
            summary.totalSpent,
            summary.spendingByCategory,
            relevantInformation
        )
    }
}
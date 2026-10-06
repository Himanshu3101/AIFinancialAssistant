package com.himanshu.aifinancialassistant.domain.tool

import com.himanshu.aifinancialassistant.domain.ai.AITool
import com.himanshu.aifinancialassistant.domain.model.TransactionType
import com.himanshu.aifinancialassistant.domain.repository.FinancialRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetTopCategoryTool @Inject constructor(
    private val financialRepository: FinancialRepository
): FinancialTool {
    override val definition = AITool(
        name = "getTopCategory",
        description =  "Returns the financial transaction category with the highest total debit spending.",
        parameters = emptyMap(),
        requiredParameter = emptyList()
    )

    override suspend fun execute(arguments: Map<String, String>): String {
        val transaction = financialRepository
            .getTransaction()
            .first()
            .filter { it.type == TransactionType.DEBIT }

        val topCategory = transaction
            .groupBy { it.category }
            .mapValues { (_, categoryTransaction) ->
                categoryTransaction.sumOf { it.amount }
            }
            .maxByOrNull { it.value }
        return topCategory?.let {(category, amount) ->
            "Top Spending category: $category with $amount"
        }?: "No Spending transaction found"
    }
}
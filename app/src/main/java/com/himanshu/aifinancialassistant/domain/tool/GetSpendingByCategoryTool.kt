package com.himanshu.aifinancialassistant.domain.tool

import com.himanshu.aifinancialassistant.domain.ai.AITool
import com.himanshu.aifinancialassistant.domain.ai.AIToolParameter
import com.himanshu.aifinancialassistant.domain.model.TransactionCategory
import com.himanshu.aifinancialassistant.domain.model.TransactionType
import com.himanshu.aifinancialassistant.domain.repository.FinancialRepository
import javax.inject.Inject

class GetSpendingByCategoryTool @Inject constructor(
    private val financialRepository: FinancialRepository
): FinancialTool {

    override val definition= AITool(
        name = "getSpendingByCategory",
        description = "Returns the total debit spending for a specific financial transaction category.",
        parameters = mapOf(
            "category" to AIToolParameter(
                type = "string",
                description = "The spending category.",
                enumValues = listOf(
                    "FOOD",
                    "SHOPPING",
                    "TRAVEL",
                    "BILLS",
                    "ENTERTAINMENT",
                    "HEALTH",
                    "OTHER"
                )
            )
        ),
        requiredParameter = listOf("category")
    )

    override suspend fun execute(arguments: Map<String, String>): String {

        val categoryName = arguments["category"]?: return "Category is required."

        val category = runCatching {
            TransactionCategory.valueOf(categoryName.uppercase())
        }.getOrNull() ?: return "Unknown Category: $categoryName"

        val transactions = financialRepository.getTransactionByCategory(category)


        val totalSpent = transactions
            .filter { it.type == TransactionType.DEBIT }
            .sumOf { it.amount }


        val result = "$categoryName spending: Rs. $totalSpent"
        return result
    }

}
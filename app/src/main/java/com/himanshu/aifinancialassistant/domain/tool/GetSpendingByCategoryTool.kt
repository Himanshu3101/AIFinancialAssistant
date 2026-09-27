package com.himanshu.aifinancialassistant.domain.tool

import com.himanshu.aifinancialassistant.domain.model.TransactionCategory
import com.himanshu.aifinancialassistant.domain.model.TransactionType
import com.himanshu.aifinancialassistant.domain.repository.FinancialRepository
import javax.inject.Inject

class GetSpendingByCategoryTool @Inject constructor(
    private val financialRepository: FinancialRepository
): FinancialTool {
    override val name: String = "getSpendingByCategory"

    override suspend fun execute(arguments: Map<String, String>): String {
        val categoryName = arguments["category"]?: return "Category is required."

        val category = runCatching {
            TransactionCategory.valueOf(categoryName.uppercase())
        }.getOrNull() ?: return "Unknown Category: $categoryName"

        val transaction = financialRepository.getTransactionByCategory(category.name)

        val totalSpent = transaction
            .filter { it.type == TransactionType.DEBIT }
            .sumOf { it.amount }

        return "$categoryName spending: Rs. $totalSpent"
    }

}
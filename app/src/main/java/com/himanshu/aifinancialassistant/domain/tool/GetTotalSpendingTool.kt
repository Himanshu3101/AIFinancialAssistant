package com.himanshu.aifinancialassistant.domain.tool

import com.himanshu.aifinancialassistant.domain.ai.AITool
import com.himanshu.aifinancialassistant.domain.model.TransactionType
import com.himanshu.aifinancialassistant.domain.repository.FinancialRepository
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetTotalSpendingTool @Inject constructor(
    private val financialRepository: FinancialRepository
): FinancialTool {

    override val definition= AITool(
        name = "getTotalSpending",
        description =  "Returns the total debit spending across all financial transactions.",
        parameters = emptyMap(),
        requiredParameter = emptyList()
    )

    override suspend fun execute(arguments: Map<String, String>): String {
        val transaction = financialRepository
            .getTransaction()
            .first()

        val totalSpent = transaction
            .filter{ it.type == TransactionType.DEBIT }
            .sumOf { it.amount }

        return "Total spending: ₹$totalSpent"
    }
}
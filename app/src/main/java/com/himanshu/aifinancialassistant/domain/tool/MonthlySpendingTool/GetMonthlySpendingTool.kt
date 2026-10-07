package com.himanshu.aifinancialassistant.domain.tool.MonthlySpendingTool

import com.himanshu.aifinancialassistant.domain.ai.AITool
import com.himanshu.aifinancialassistant.domain.ai.AIToolParameter
import com.himanshu.aifinancialassistant.domain.model.TransactionType
import com.himanshu.aifinancialassistant.domain.repository.FinancialRepository
import com.himanshu.aifinancialassistant.domain.tool.FinancialTool
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetMonthlySpendingTool @Inject constructor(
    private val financialRepository: FinancialRepository
) : FinancialTool {
    override val definition = AITool(
        name = "getMonthlySpending",
        description = "Returns the total debit spending for a specific month.",
        parameters = mapOf(
            "month" to AIToolParameter(
                type = "string",
                description = "The month in YYYY-MM format, for example 2026-09",
                enumValues = null
            )
        ),
        requiredParameter = listOf("month")
    )

    override suspend fun execute(arguments: Map<String, String>): String {
        val month = arguments["month"]
            ?: return "Month is required in YYYY-MM format."

        if(!month.matches(Regex("\\d{4}-\\d{2}"))){
            return "Invalid month format. Use YYYY-MM."
        }

        val transactions = financialRepository
            .getTransaction()
            .first()

        val totalSpent = transactions
            .filter { transaction ->
                transaction.type == TransactionType.DEBIT &&
                        transaction.date.startsWith(month)
            }.sumOf { it.amount }
        return "Spending for $month: $totalSpent"
    }
}
package com.himanshu.aifinancialassistant.domain.usecase

import com.himanshu.aifinancialassistant.domain.model.FinancialSummary
import com.himanshu.aifinancialassistant.domain.model.TransactionType
import com.himanshu.aifinancialassistant.domain.repository.FinancialRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetFinancialSummaryUseCase @Inject constructor(
    private val repository: FinancialRepository
) {
    suspend operator fun invoke(): FinancialSummary {
        val transactions = repository.getTransaction().first()

        val totalSpent = transactions
            .filter { it.type == TransactionType.DEBIT }
            .sumOf { it.amount }

        val spendingByCategory = transactions
            .filter { it.type == TransactionType.DEBIT }
            .groupBy { it.category }
            .mapValues { (_, transactions) ->
                transactions.sumOf { it.amount }
            }

        return FinancialSummary(
            totalSpent = totalSpent,
            spendingByCategory = spendingByCategory
        )
    }
}
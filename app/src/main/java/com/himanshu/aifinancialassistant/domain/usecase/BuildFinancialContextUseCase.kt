package com.himanshu.aifinancialassistant.domain.usecase

import com.himanshu.aifinancialassistant.domain.model.FinancialContext
import com.himanshu.aifinancialassistant.domain.model.TransactionType
import com.himanshu.aifinancialassistant.domain.repository.FinancialRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class BuildFinancialContextUseCase @Inject constructor(
    private val getFinancialSummaryUseCase: GetFinancialSummaryUseCase
) {
    suspend operator fun invoke(): FinancialContext {
        val summary = getFinancialSummaryUseCase()

        return FinancialContext(
            summary.totalSpent,
            summary.spendingByCategory
        )
    }
}
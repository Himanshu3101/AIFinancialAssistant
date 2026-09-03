package com.himanshu.aifinancialassistant.domain.model

data class FinancialSummary(
    val totalSpent: Double,
    val spendingByCategory: Map<TransactionCategory, Double>
)

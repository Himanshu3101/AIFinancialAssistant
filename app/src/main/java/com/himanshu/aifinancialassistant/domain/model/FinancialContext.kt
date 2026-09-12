package com.himanshu.aifinancialassistant.domain.model

data class FinancialContext(
    val totalSpent: Double,
    val spendingByCategory: Map<TransactionCategory, Double>
)
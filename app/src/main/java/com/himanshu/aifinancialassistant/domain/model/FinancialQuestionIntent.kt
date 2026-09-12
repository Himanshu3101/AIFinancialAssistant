package com.himanshu.aifinancialassistant.domain.model

data class FinancialQuestionIntent(
    val type: FinancialQuestionType,
    val category: TransactionCategory? = null
)

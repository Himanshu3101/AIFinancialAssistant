package com.himanshu.aifinancialassistant.presentation.transaction

import com.himanshu.aifinancialassistant.domain.model.FinancialSummary
import com.himanshu.aifinancialassistant.domain.model.Transaction

data class TransactionUiState(
    val isLoading: Boolean = false,
    val transactions: List<Transaction> = emptyList(),
    val financialSummary: FinancialSummary? = null,
    val error:String? = null

)

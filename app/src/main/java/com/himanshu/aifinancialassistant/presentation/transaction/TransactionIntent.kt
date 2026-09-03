package com.himanshu.aifinancialassistant.presentation.transaction

sealed interface TransactionIntent {

    data object Loadtransactions: TransactionIntent

    data object refreshTransactions: TransactionIntent
}
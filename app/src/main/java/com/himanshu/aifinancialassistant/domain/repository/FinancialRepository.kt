package com.himanshu.aifinancialassistant.domain.repository

import com.himanshu.aifinancialassistant.domain.model.Transaction
import com.himanshu.aifinancialassistant.domain.model.TransactionCategory
import kotlinx.coroutines.flow.Flow

interface FinancialRepository {

    fun getTransaction(): Flow<List<Transaction>>

    suspend fun getTransactionByCategory(
        category: TransactionCategory
    ): List<Transaction>

    suspend fun syncTransactions()
}
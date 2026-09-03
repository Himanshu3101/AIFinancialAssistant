package com.himanshu.aifinancialassistant.domain.repository

import com.himanshu.aifinancialassistant.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

interface FinancialRepository {

    fun getTransaction(): Flow<List<Transaction>>

    suspend fun getTransactionByCategory(
        category: String
    ): List<Transaction>

    suspend fun syncTransactions()
}
package com.himanshu.aifinancialassistant.data.repositoryImpl

import com.himanshu.aifinancialassistant.data.local.TransactionDao
import com.himanshu.aifinancialassistant.data.local.todomain
import com.himanshu.aifinancialassistant.data.remote.FakeTransactionApi
import com.himanshu.aifinancialassistant.domain.model.Transaction
import com.himanshu.aifinancialassistant.domain.repository.FinancialRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

class FinancialRepositoryImpl @Inject constructor(
    private val transactionDao: TransactionDao,
    private val api: FakeTransactionApi
): FinancialRepository{
    override fun getTransaction(): Flow<List<Transaction>> {
        return transactionDao.getTransactions().map {entities ->
            entities.map{it.todomain()}
        }
    }

    override suspend fun getTransactionByCategory(category: String): List<Transaction> {
        return transactionDao.getTransactionsByCategory(category).map{ it.todomain() }
    }

    override suspend fun syncTransactions() {
        val transaction = api.getTransactions()
        transactionDao.insertTransactions(transaction)
    }
}
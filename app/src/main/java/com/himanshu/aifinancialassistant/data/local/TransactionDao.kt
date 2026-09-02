package com.himanshu.aifinancialassistant.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import androidx.room.OnConflictStrategy

@Dao
interface TransactionDao {

    @Query("SELECT * FROM transactions ORDER BY date DESC")
    fun getTransactions(): Flow<List<TransactionEntity>>

    @Query(
"SELECT * FROM transactions " +
        "WHERE category = :category " +
        "ORDER BY date DESC"
    )
    suspend fun getTransactionsByCategory(
        category: String
    ): List<TransactionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransactions(
        transaction: List<TransactionEntity>
    )
}


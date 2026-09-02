package com.himanshu.aifinancialassistant.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey
    val id:String,
    val merchant: String,

    val amount: Double,

    val category: String,

    val date: String,

    val type: String
)

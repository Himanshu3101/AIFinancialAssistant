package com.himanshu.aifinancialassistant.data.remote

import com.himanshu.aifinancialassistant.data.local.TransactionEntity
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

class FakeTransactionApi @Inject constructor(){

    suspend fun getTransactions(): List<TransactionEntity>{
        delay(500.milliseconds)

        return listOf(
            TransactionEntity(
                id = "TXN001",
                merchant = "Amazon",
                amount = 2499.0,
                category = "Shopping",
                date = "2026-09-01",
                type = "DEBIT"
            ),
            TransactionEntity(
                id = "TXN002",
                merchant = "Swiggy",
                amount = 589.0,
                category = "Food",
                date = "2026-09-01",
                type = "DEBIT"
            ),
            TransactionEntity(
                id = "TXN003",
                merchant = "Uber",
                amount = 342.0,
                category = "Travel",
                date = "2026-08-31",
                type = "DEBIT"
            ),
            TransactionEntity(
                id = "TXN004",
                merchant = "Netflix",
                amount = 649.0,
                category = "Entertainment",
                date = "2026-08-30",
                type = "DEBIT"
            )
        )
    }
}
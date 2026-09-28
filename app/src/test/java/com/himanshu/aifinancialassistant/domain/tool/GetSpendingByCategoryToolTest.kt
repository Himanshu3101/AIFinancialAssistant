package com.himanshu.aifinancialassistant.domain.tool

import com.himanshu.aifinancialassistant.domain.model.Transaction
import com.himanshu.aifinancialassistant.domain.model.TransactionCategory
import com.himanshu.aifinancialassistant.domain.model.TransactionType
import com.himanshu.aifinancialassistant.domain.repository.FinancialRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetSpendingByCategoryToolTest {

    private val fakeRepository = object : FinancialRepository{
        override fun getTransaction(): Flow<List<Transaction>> {
           return flowOf(
               listOf(
                   Transaction(
                       id = "1",
                       merchant = "Swiggy",
                       amount = 589.0,
                       category = TransactionCategory.FOOD,
                       date = "2026-09-01",
                       type = TransactionType.DEBIT
                   ),
                   Transaction(
                       id = "2",
                       merchant = "Amazon",
                       amount = 2499.0,
                       category = TransactionCategory.SHOPPING,
                       date = "2026-09-01",
                       type = TransactionType.DEBIT
                   )
               )
           )
        }

        override suspend fun getTransactionByCategory(category: TransactionCategory): List<Transaction> {
            return when(category) {
                TransactionCategory.FOOD -> listOf(
                    Transaction(
                        id = "1",
                        merchant = "Swiggy",
                        amount = 589.0,
                        category = TransactionCategory.FOOD,
                        date = "2026-09-01",
                        type = TransactionType.DEBIT
                    )
                )

                TransactionCategory.SHOPPING -> listOf(
                    Transaction(
                        id = "2",
                        merchant = "Amazon",
                        amount = 2499.0,
                        category = TransactionCategory.SHOPPING,
                        date = "2026-09-01",
                        type = TransactionType.DEBIT
                    )
                )

                else -> emptyList()
            }
        }

       /* override suspend fun getTransactionByCategory(category: String): List<Transaction> {
            return when (category){
                "FOOD" -> listOf(
                    Transaction(
                        id = "1",
                        merchant = "Swiggy",
                        amount = 589.0,
                        category = TransactionCategory.FOOD,
                        date = "2026-09-01",
                        type = TransactionType.DEBIT
                    )
                )

                "SHOPPING" -> listOf(
                    Transaction(
                        id = "2",
                        merchant = "Amazon",
                        amount = 2499.0,
                        category = TransactionCategory.SHOPPING,
                        date = "2026-09-01",
                        type = TransactionType.DEBIT
                    )
                )

                else -> emptyList()
            }
        }*/

        override suspend fun syncTransactions() {
            TODO("Not yet implemented")
        }

    }

    private val tool = GetSpendingByCategoryTool(
        financialRepository = fakeRepository
    )

    @Test
    fun `returns from spending`() = runTest {
        val result = tool.execute(
            mapOf("category" to "FOOD")
        )
        assertEquals(
            "FOOD spending: Rs. 589.0",
            result
        )
    }

    @Test
    fun `returns error when category is missing`() = runTest {
        val result = tool.execute(emptyMap())
        assertEquals(
            "Category is required.",
            result
        )
    }

    @Test
    fun `return error for unknown category`() = runTest {
        val result = tool.execute(
            mapOf("category" to "XYZ")
        )

        assertEquals(
            "Unknown Category: XYZ",
            result
        )
    }
}
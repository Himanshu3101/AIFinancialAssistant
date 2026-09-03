package com.himanshu.aifinancialassistant

import com.himanshu.aifinancialassistant.domain.model.Transaction
import com.himanshu.aifinancialassistant.domain.model.TransactionCategory
import com.himanshu.aifinancialassistant.domain.model.TransactionType
import com.himanshu.aifinancialassistant.domain.repository.FinancialRepository
import com.himanshu.aifinancialassistant.domain.usecase.GetFinancialSummaryUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


class GetFinancialSummaryUseCaseTest {

    private lateinit var repository: FakeFinancialRepository
    private lateinit var useCase: GetFinancialSummaryUseCase

    @Before
    fun setup(){
        repository = FakeFinancialRepository()
        useCase = GetFinancialSummaryUseCase(repository)
    }

    @Test
    fun `should calculate total debit spending`() = runTest {
        repository.transactions = listOf(
            Transaction(
                id = "1",
                merchant = "Amazon",
                amount = 2499.0,
                category = TransactionCategory.SHOPPING,
                date = "2026-09-01",
                type = TransactionType.DEBIT
            ),
            Transaction(
                id = "2",
                merchant = "Swiggy",
                amount = 589.0,
                category = TransactionCategory.FOOD,
                date = "2026-09-01",
                type = TransactionType.DEBIT
            ),
            Transaction(
                id = "3",
                merchant = "Salary",
                amount = 50000.0,
                category = TransactionCategory.OTHER,
                date = "2026-09-01",
                type = TransactionType.CREDIT
            )
        )

        val result = useCase()

        assertEquals(
            3088.0,
            result.totalSpent,
            0.0
        )
    }

    @Test
    fun `should group debit spending by category`() = runTest {

        repository.transactions = listOf(
            Transaction(
                id = "1",
                merchant = "Amazon",
                amount = 2000.0,
                category = TransactionCategory.SHOPPING,
                date = "2026-09-01",
                type = TransactionType.DEBIT
            ),
            Transaction(
                id = "2",
                merchant = "Flipkart",
                amount = 1000.0,
                category = TransactionCategory.SHOPPING,
                date = "2026-09-01",
                type = TransactionType.DEBIT
            ),
            Transaction(
                id = "3",
                merchant = "Swiggy",
                amount = 500.0,
                category = TransactionCategory.FOOD,
                date = "2026-09-01",
                type = TransactionType.DEBIT
            )
        )

        val result = useCase()

        assertEquals(
            3000.0,
            result.spendingByCategory[TransactionCategory.SHOPPING]
        )

        assertEquals(
            500.0,
            result.spendingByCategory[TransactionCategory.FOOD]
        )
    }

    @Test
    fun `should ignore credit transactions`() = runTest {

        repository.transactions = listOf(
            Transaction(
                id = "1",
                merchant = "Amazon",
                amount = 1000.0,
                category = TransactionCategory.SHOPPING,
                date = "2026-09-01",
                type = TransactionType.DEBIT
            ),
            Transaction(
                id = "2",
                merchant = "Salary",
                amount = 50000.0,
                category = TransactionCategory.OTHER,
                date = "2026-09-01",
                type = TransactionType.CREDIT
            )
        )

        val result = useCase()

        assertEquals(
            1000.0,
            result.totalSpent,
            0.0
        )

        assertEquals(
            null,
            result.spendingByCategory[TransactionCategory.OTHER]
        )
    }
    private class FakeFinancialRepository: FinancialRepository {

        var transactions: List<Transaction> = emptyList()

        override fun getTransaction(): Flow<List<Transaction>> {
            return flowOf(transactions)
        }

        override suspend fun getTransactionByCategory(category: String): List<Transaction> {
            return transactions.filter{
                it.category.name == category
            }
        }

        override suspend fun syncTransactions() {
            TODO("Not yet implemented")
        }
    }
}

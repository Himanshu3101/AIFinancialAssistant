package com.himanshu.aifinancialassistant.domain.usecase

import com.himanshu.aifinancialassistant.domain.model.Transaction
import com.himanshu.aifinancialassistant.domain.repository.FinancialRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTransactionsUseCase @Inject constructor(
    private val repository: FinancialRepository
) {

    operator fun invoke(): Flow<List<Transaction>>{
        return repository.getTransaction()
    }
}
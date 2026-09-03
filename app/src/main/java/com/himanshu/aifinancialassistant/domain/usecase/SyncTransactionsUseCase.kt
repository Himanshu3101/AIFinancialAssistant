package com.himanshu.aifinancialassistant.domain.usecase

import com.himanshu.aifinancialassistant.domain.repository.FinancialRepository
import javax.inject.Inject

class SyncTransactionsUseCase @Inject constructor(
    private val repository: FinancialRepository
) {
    suspend operator fun invoke(){
        repository.syncTransactions()
    }
}
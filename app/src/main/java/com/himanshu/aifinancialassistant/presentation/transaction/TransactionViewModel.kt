package com.himanshu.aifinancialassistant.presentation.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.himanshu.aifinancialassistant.domain.usecase.GetTransactionsUseCase
import com.himanshu.aifinancialassistant.domain.usecase.SyncTransactionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val getTransactionsUseCase: GetTransactionsUseCase,
    private val syncTransactionsUseCase: SyncTransactionsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TransactionUiState())
    val uiState: StateFlow<TransactionUiState> = _uiState.asStateFlow()

    init {
        observeTransactions()
        onIntent(TransactionIntent.Loadtransactions)
    }

    fun onIntent(intent: TransactionIntent){
        when(intent) {
            TransactionIntent.Loadtransactions -> {
                syncTransactions()
            }
            TransactionIntent.refreshTransactions -> {
                syncTransactions()
            }
        }
    }

    private fun observeTransactions() {
        viewModelScope.launch {
            getTransactionsUseCase().collect{ transactions ->
                _uiState.value = _uiState.value.copy(
                    transactions = transactions
                )
            }
        }
    }

    private fun syncTransactions() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null
            )

            try {
                syncTransactionsUseCase()
                _uiState.value = _uiState.value.copy(
                    isLoading = false
                )
            }catch (e: Exception){
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Unable to load transactions"
                )
            }

        }
    }
}
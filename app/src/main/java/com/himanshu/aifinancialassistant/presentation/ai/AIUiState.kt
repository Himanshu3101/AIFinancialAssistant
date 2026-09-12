package com.himanshu.aifinancialassistant.presentation.ai

data class AIUiState(
    val isLoading: Boolean = false,
    val response: String = "",
    val error: String? = null
)

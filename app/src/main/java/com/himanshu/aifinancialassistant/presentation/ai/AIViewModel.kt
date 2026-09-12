package com.himanshu.aifinancialassistant.presentation.ai

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.himanshu.aifinancialassistant.domain.usecase.AskFinancialAssistantUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AIViewModel @Inject constructor(
    private val askFinancialAssistantUseCase: AskFinancialAssistantUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(AIUiState())
    val uiState: StateFlow<AIUiState> = _uiState.asStateFlow()

    fun onIntent(intent: AIIntent){
        when(intent){
            is AIIntent.AskQuestion -> askQuestion(intent.question)
        }
    }


    private fun askQuestion(question: String) {
        viewModelScope.launch {

            _uiState.value = AIUiState(
                isLoading = true,
                response = "",
                error = null
            )

            try{
                val response = askFinancialAssistantUseCase(question)
                _uiState.value = AIUiState(
                    isLoading = false,
                    response = response,
                    error = null
                )
            }catch (e: Exception){
                e.printStackTrace()
                _uiState.value = AIUiState(
                    isLoading = false,
                    response = "",
                    error = "${e.javaClass.simpleName}: ${e.message}"
                )
            }
        }
    }
}

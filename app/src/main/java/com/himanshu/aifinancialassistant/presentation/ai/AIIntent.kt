package com.himanshu.aifinancialassistant.presentation.ai

sealed interface AIIntent {

    data class AskQuestion(
        val question: String
    ): AIIntent
}
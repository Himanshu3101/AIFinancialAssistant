package com.himanshu.aifinancialassistant.domain.usecase

import com.himanshu.aifinancialassistant.domain.model.FinancialQuestionIntent
import com.himanshu.aifinancialassistant.domain.model.FinancialQuestionType
import com.himanshu.aifinancialassistant.domain.model.TransactionCategory
import javax.inject.Inject

class ClassifyFinancialQuestionUseCase @Inject constructor() {

    operator fun invoke(question: String): FinancialQuestionIntent{
        val normalizedQuestion = question.lowercase().trim()

        return when{

            normalizedQuestion.contains("total") && normalizedQuestion.contains("spend") -> {
                FinancialQuestionIntent(type = FinancialQuestionType.TOTAL_SPENDING)
            }

            normalizedQuestion.contains("most") ||
                    normalizedQuestion.contains("highest") ||
                    normalizedQuestion.contains("maximum") -> {
                FinancialQuestionIntent(type = FinancialQuestionType.TOP_CATEGORY)
            }

            normalizedQuestion.contains("food") -> {
                FinancialQuestionIntent(
                    type = FinancialQuestionType.CATEGORY_SPENDING,
                    category = TransactionCategory.FOOD
                )
            }
                    normalizedQuestion.contains("shopping") -> {
                        FinancialQuestionIntent(
                            type = FinancialQuestionType.CATEGORY_SPENDING,
                            category = TransactionCategory.SHOPPING
                        )
                    }


                    normalizedQuestion.contains("travel") -> {
                        FinancialQuestionIntent(
                            type = FinancialQuestionType.CATEGORY_SPENDING,
                            category = TransactionCategory.TRAVEL
                        )
                    }

                    normalizedQuestion.contains("entertainment") -> {
                        FinancialQuestionIntent(
                            type = FinancialQuestionType.CATEGORY_SPENDING,
                            category = TransactionCategory.ENTERTAINMENT
                        )
                    }

            else -> {
                FinancialQuestionIntent(
                    type = FinancialQuestionType.GENERAL
                )
            }
        }
    }
}
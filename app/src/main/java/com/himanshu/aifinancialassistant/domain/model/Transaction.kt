package com.himanshu.aifinancialassistant.domain.model

data class Transaction(
    val id : String,
    val merchant: String,
    val amount: Double,
    val category: TransactionCategory,
    val date: String,
    val type: TransactionType
)

enum class TransactionCategory{
    FOOD,
    SHOPPING,
    TRAVEL,
    BILLS,
    ENTERTAINMENT,
    HEALTH,
    OTHER
}

enum class TransactionType{
    DEBIT,
    CREDIT
}
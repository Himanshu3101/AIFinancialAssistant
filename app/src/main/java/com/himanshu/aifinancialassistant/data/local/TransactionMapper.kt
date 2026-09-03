package com.himanshu.aifinancialassistant.data.local

import com.himanshu.aifinancialassistant.domain.model.Transaction
import com.himanshu.aifinancialassistant.domain.model.TransactionCategory
import com.himanshu.aifinancialassistant.domain.model.TransactionType

fun TransactionEntity.todomain(): Transaction{
    return Transaction(
        id = id,
        merchant = merchant,
        amount = amount,
        category = TransactionCategory.valueOf(category.uppercase()),
        date = date,
        type = TransactionType.valueOf(type.uppercase())
    )
}

fun Transaction.toEntity(): TransactionEntity{
    return TransactionEntity(
        id = id,
        merchant = merchant,
        amount = amount,
        category = category.name,
        date = date,
        type = type.name
    )
}
package com.himanshu.aifinancialassistant.data.remote

import com.himanshu.aifinancialassistant.domain.model.FinancialContext
import javax.inject.Inject

class FinancialContextFormatter @Inject constructor() {

    fun format(context: FinancialContext): String{
        val categories = context.spendingByCategory
            .entries
            .joinToString ("\n"){ (category, amount) ->
                "- ${category.name}: Rs. $amount"
            }

        return """
            Financial Summary:
            Total Spending: Rs. ${context.totalSpent}
            
            Spending by category:
            $categories
        """.trimIndent()
    }
}
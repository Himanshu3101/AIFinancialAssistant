package com.himanshu.aifinancialassistant.domain.tool

import com.himanshu.aifinancialassistant.domain.ai.AITool
import com.himanshu.aifinancialassistant.domain.ai.AIToolParameter
import javax.inject.Inject

class FinancialToolRegistry @Inject constructor(
    private val getSpendingByCategoryTool: GetSpendingByCategoryTool
) {
    private val tools: Map<String, FinancialTool> = mapOf(
        getSpendingByCategoryTool.name to getSpendingByCategoryTool
    )

    fun getTool(name:String): FinancialTool?{
        return tools[name]
    }

    fun getToolDefinitions(): List<AITool>{
        return listOf(
            AITool(
                name = getSpendingByCategoryTool.name,
                description = "Returns the total debit spending for a specific financial transaction category.",
                parameters = mapOf(
                    "category" to AIToolParameter(
                        type = "string",
                        description = "The spending category.",
                        enumValues = listOf(
                            "FOOD",
                            "SHOPPING",
                            "TRAVEL",
                            "BILLS",
                            "ENTERTAINMENT",
                            "HEALTH",
                            "OTHER"
                        )
                    )
                ),
                requiredParameter = listOf("category")
            )
        )
    }
}
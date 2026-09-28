package com.himanshu.aifinancialassistant.domain.tool

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
}
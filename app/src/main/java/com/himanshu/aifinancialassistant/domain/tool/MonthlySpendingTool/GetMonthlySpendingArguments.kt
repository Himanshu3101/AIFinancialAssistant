package com.himanshu.aifinancialassistant.domain.tool.MonthlySpendingTool

//Typed tool argument
data class GetMonthlySpendingArguments(
    val month: String
){
    companion object{

        fun from(arguments: Map<String, String>): GetMonthlySpendingArguments?{
            val month = arguments["month"] ?: return null
            return GetMonthlySpendingArguments(month = month)
        }
    }
}

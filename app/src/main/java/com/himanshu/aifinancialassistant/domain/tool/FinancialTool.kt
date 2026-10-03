package com.himanshu.aifinancialassistant.domain.tool


//execution Tool - What our application actually executes
interface FinancialTool {
    val name: String

    suspend fun execute(
        arguments: Map<String, String>
    ): String

}
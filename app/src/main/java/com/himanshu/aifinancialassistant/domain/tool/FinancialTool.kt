package com.himanshu.aifinancialassistant.domain.tool

import com.himanshu.aifinancialassistant.domain.ai.AITool


//execution Tool - What our application actually executes
interface FinancialTool {

    val definition: AITool
    suspend fun execute(
        arguments: Map<String, String>
    ): String

}
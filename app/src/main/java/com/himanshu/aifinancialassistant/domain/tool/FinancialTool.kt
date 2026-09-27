package com.himanshu.aifinancialassistant.domain.tool

interface FinancialTool {
    val name: String

    suspend fun execute(
        arguments: Map<String, String>
    ): String

}
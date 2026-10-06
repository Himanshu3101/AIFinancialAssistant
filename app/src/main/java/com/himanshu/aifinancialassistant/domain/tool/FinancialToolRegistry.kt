package com.himanshu.aifinancialassistant.domain.tool

import com.himanshu.aifinancialassistant.domain.ai.AITool
import javax.inject.Inject

class FinancialToolRegistry @Inject constructor(
    private val tools: Set<@JvmSuppressWildcards FinancialTool>
) {
    fun getTool(name:String): FinancialTool?{
        return tools.firstOrNull {
            it.definition.name == name
        }
    }

    fun getToolDefinitions(): List<AITool>{
        return tools.map {
            it.definition
        }
    }
}
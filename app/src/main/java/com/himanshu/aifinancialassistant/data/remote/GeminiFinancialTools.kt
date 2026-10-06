package com.himanshu.aifinancialassistant.data.remote

import com.himanshu.aifinancialassistant.data.remote.model.GeminiFunctionDeclaration
import com.himanshu.aifinancialassistant.data.remote.model.FunctionParameters
import com.himanshu.aifinancialassistant.data.remote.model.PropertyDefinition
import com.himanshu.aifinancialassistant.data.remote.model.Tool

object GeminiFinancialTools {

    val getSpendingByCategory = Tool(
        functionDeclarations = listOf(
            GeminiFunctionDeclaration(
                name = "getSpendingByCategory",
                description = """ Returns the total debit spending for a specific financial transaction category.""".trimIndent(),
                parameters = FunctionParameters(
                    type = "object",
                    properties = mapOf(
                        "category" to PropertyDefinition(
                            type = "string",
                            description = "The spending category.",
                            enum = listOf(
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
                    required = listOf("category")
                )
            )
        )
    )
}
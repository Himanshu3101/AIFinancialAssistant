package com.himanshu.aifinancialassistant.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class Tool(
    val functionDeclarations: List<FunctionDeclaration>
)


@Serializable
data class FunctionDeclaration(
    val name: String,
    val description: String,
    val parameters: FunctionParameters
)

@Serializable
data class FunctionParameters(
    val type: String,
    val description: String? = null,
    val properties: Map<String, PropertyDefinition>,
    val required: List<String> = emptyList()
)

@Serializable
data class PropertyDefinition(
    val type: String,
    val description: String? = null,
    val enum: List<String>? = null
)
package com.himanshu.aifinancialassistant.domain.ai


//What our application got after executing a tool
data class AIToolResult(
    val id: String,
    val name: String,
    val result: String
)
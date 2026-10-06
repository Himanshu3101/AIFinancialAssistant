package com.himanshu.aifinancialassistant.data.remote

import com.himanshu.aifinancialassistant.data.remote.gemini.FunctionCall

fun FunctionCall.toToolArguments(): Map<String, String>{
    return args.mapValues { (_, value) ->
        value.toString().removeSurrounding("\"")
    }
}
package com.himanshu.aifinancialassistant.data.repositoryImpl

import android.util.Log
import com.himanshu.aifinancialassistant.data.remote.gemini.GeminiService
import com.himanshu.aifinancialassistant.data.remote.GeminiFinancialTools
import com.himanshu.aifinancialassistant.data.remote.gemini.GeminiModelRequest
import com.himanshu.aifinancialassistant.data.remote.gemini.GeminiContent
import com.himanshu.aifinancialassistant.data.remote.gemini.FunctionCallingConfig
import com.himanshu.aifinancialassistant.data.remote.gemini.FunctionResponse
import com.himanshu.aifinancialassistant.data.remote.gemini.Part
import com.himanshu.aifinancialassistant.data.remote.gemini.SystemInstruction
import com.himanshu.aifinancialassistant.data.remote.gemini.ToolConfig
import com.himanshu.aifinancialassistant.data.remote.model.toText
import com.himanshu.aifinancialassistant.data.remote.toToolArguments
import com.himanshu.aifinancialassistant.domain.repository.AIRepository
import com.himanshu.aifinancialassistant.domain.tool.FinancialToolRegistry
import javax.inject.Inject

class AIRepositoryImpl @Inject constructor(
    private val geminiService: GeminiService,
    private val financialToolRegistry: FinancialToolRegistry
) : AIRepository {

    override suspend fun askFinancialAssistant(
        systemPrompt: String,
        context: String,
        userPrompt: String
    ): String {

        // FIRST REQUEST
        // User → Gemini → FunctionCall

        val firstRequest = GeminiModelRequest(
            systemInstruction = SystemInstruction(
                parts = listOf(
                    Part(text = systemPrompt)
                )
            ),
            contents = listOf(
                GeminiContent(
                    role = "user",
                    parts = listOf(
                        Part(
                            text = userPrompt
                            /*text = """
                            Financial Context:
                            $context

                            User Question:
                            $userPrompt
                        """.trimIndent()*/
                        )
                    )
                )
            ),
            tools = listOf(
                GeminiFinancialTools.getSpendingByCategory
            ),
            toolConfig = ToolConfig(
                functionCallingConfig = FunctionCallingConfig(
                    mode = "ANY"
                )
            )
        )

        Log.d("AI_FLOW", "FIRST GEMINI CALL START")

        /*val firstResponse = aiService.generateResponse(firstRequest)*/

        val firstResponse = try {
            geminiService.generateResponse(firstRequest)
        } catch (e: retrofit2.HttpException) {

            Log.e("AI_FLOW", "FIRST GEMINI HTTP ERROR = ${e.code()}")

            Log.e(
                "AI_FLOW",
                "FIRST GEMINI ERROR BODY = ${
                    e.response()?.errorBody()?.string()
                }"
            )

            throw e
        }

        Log.d("AI_FLOW", "FIRST GEMINI CALL SUCCESS")
        Log.d("AI_FLOW", "FUNCTION CALL = ${firstResponse.candidates}")


        val modelContent = firstResponse.candidates
            .firstOrNull()
            ?.content
            ?: return firstResponse.toText()

        val functionCallPart = modelContent.parts
            .firstOrNull{ it.functionCall != null}

        val functionCall = functionCallPart?.functionCall ?: return firstResponse.toText()

        //If Gemini answered directly

        // Find matching Kotlin tool
        val tool = financialToolRegistry.getTool(functionCall.name)?: throw IllegalArgumentException(
            "Unknow tool: ${functionCall.name}"
        )

        //Convert Gemini arguments
        val arguments = functionCall.toToolArguments()

        //Execute Kotlin Code
        val toolResult = tool.execute(arguments)




//Second Request -> FunctionCall -> Gemini -> Final text

        val modelResponseParts = modelContent.parts.map { responsePart->
            Part(
                text = responsePart.text.toString(),
                functionCall = responsePart.functionCall,
                thoughtSignature = responsePart.thoughtSignature
            )
        }

        Log.d(
            "AI_FLOW",
            "MODEL RESPONSE PARTS = $modelResponseParts"
        )

        val functionResponse = FunctionResponse(
            id = functionCall.id
            ?:throw IllegalArgumentException("Function call ID is missing"),
            name = functionCall.name,
            response = mapOf("result" to toolResult)
        )

        val secondRequest = GeminiModelRequest(
            systemInstruction = SystemInstruction(parts = listOf(
                Part(text = systemPrompt)
            )),
            contents = listOf(
                //Original User Question
                GeminiContent(
                    role = "user",
                    parts = listOf(
                        Part(text = userPrompt)
                    )
                ),

                //Gemini's originalfunction Call
                GeminiContent(
                    role = "model",
                    parts = modelResponseParts
                ),

                //Our tool response
                GeminiContent(
                    role = "user",
                    parts = listOf(
                        Part(
                            functionResponse = functionResponse
                        )
                    )
                )
            ),

            tools = listOf(
                GeminiFinancialTools.getSpendingByCategory
            ),

            toolConfig = ToolConfig(
                functionCallingConfig = FunctionCallingConfig(
                    mode = "AUTO"
                )
            )
        )

        Log.d("AI_FLOW", "SECOND GEMINI CALL START")
        Log.d("AI_FLOW", "TOOL RESULT = $toolResult")

       /* val finalResponse = aiService.generateResponse(secondRequest)
        Log.d(
            "AI_FLOW",
            "FINAL GEMINI RESPONSE = ${finalResponse.toText()}"
        )
        return finalResponse.toText()*/


        val finalResponse = try {
            geminiService.generateResponse(secondRequest)
        } catch (e: retrofit2.HttpException) {

            Log.e(
                "AI_FLOW",
                "SECOND GEMINI HTTP ERROR = ${e.code()}"
            )

            Log.e(
                "AI_FLOW",
                "SECOND GEMINI ERROR BODY = ${
                    e.response()?.errorBody()?.string()
                }"
            )

            throw e
        }

        Log.d("AI_FLOW", "SECOND GEMINI CALL SUCCESS")

        return finalResponse.toText()






//        val functionCall = firstResponse.candidates
//            .flatMap { it.content?.parts.orEmpty() }
//            .firstNotNullOfOrNull { it.functionCall }
//
//        if(functionCall != null){
//
//
//
//            return toolResult
//        }
//
//        val result = firstResponse.toText()
//        if(result.isBlank()){
//            throw IllegalArgumentException("AI returned an empty response")
//        }
//        return firstResponse.toText()
    }
}
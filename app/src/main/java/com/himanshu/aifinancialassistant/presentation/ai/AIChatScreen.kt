package com.himanshu.aifinancialassistant.presentation.ai

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AIChatScreen(
    viewModel: AIViewModel
){
    val uiState by viewModel.uiState.collectAsState()

    var question by remember {
        mutableStateOf("")
    }

    MainScreen(
        question = question,
        onQuestionChange = { question = it },
        onAskClick = {
            viewModel.onIntent(
                AIIntent.AskQuestion(question)
            )
        },
        uiState =  uiState)
}

@Composable
private fun MainScreen(
    question: String,
    onQuestionChange: (String) -> Unit,
    onAskClick: () -> Unit,
    uiState: AIUiState,

) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top
    ){
        Text(
            text = "AI Financial Assistant",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = question,
            onValueChange = onQuestionChange,
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Ask a Financial Questions")
            },
            placeholder = {
                Text(
                    text = "How much did I spend on food?"
                )
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = onAskClick,
            modifier = Modifier.fillMaxWidth(),
            enabled = !uiState.isLoading
        ){
            Text("Ask AI")
        }

        Spacer(modifier = Modifier.height(24.dp))

        when{
            uiState.isLoading -> {
                CircularProgressIndicator()
            }

            uiState.error != null -> {
                Text(
                    text = uiState.error,
                    color = MaterialTheme.colorScheme.error
                )
            }

            uiState.response.isNotBlank() -> {
                Text(
                    text = "AI Response",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = uiState.response
                )
            }
        }
    }
}
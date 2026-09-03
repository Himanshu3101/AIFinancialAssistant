package com.himanshu.aifinancialassistant.presentation.transaction

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.himanshu.aifinancialassistant.domain.model.Transaction
import com.himanshu.aifinancialassistant.ui.theme.AIFinancialAssistantTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionScreen : ComponentActivity() {
    private val viewModel: TransactionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AIFinancialAssistantTheme {
                val uiState by viewModel.uiState.collectAsState()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TransactionScreen(
                        uiState = uiState,
                        onIntent = viewModel::onIntent,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun TransactionScreen(
    uiState: TransactionUiState,
    modifier: Modifier = Modifier,
    onIntent: (TransactionIntent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
    ) {
        FinancialSummary(uiState)
        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Button(
            onClick = {
                onIntent(TransactionIntent.refreshTransactions)
            },
            enabled = !uiState.isLoading,
            modifier = Modifier
                .fillMaxWidth()

        ) {
            ScreenFlow(uiState)
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        FlowScreen(
            uiState = uiState
        )
    }
}

@Composable
fun FinancialSummary(uiState: TransactionUiState) {
    uiState.financialSummary?.let { summary ->
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Total Spent",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "Rs. ${summary.totalSpent}",
                style = MaterialTheme.typography.headlineMedium
            )

            summary.spendingByCategory.forEach { (category, amount) ->
                Text(
                    text = "${category.name}: ₹$amount",
                )
            }
        }
    }
}

@Composable
fun ScreenFlow(uiState: TransactionUiState) {
    if (uiState.isLoading) {
        CircularProgressIndicator(
            modifier = Modifier.size(20.dp),
            strokeWidth = 2.dp
        )
        Spacer(
            modifier = Modifier.width(8.dp)
        )
        Text("Refreshing..,")
    } else {
        Text("Refresh")
    }
}

@Composable
fun FlowScreen(uiState: TransactionUiState) {
    if (uiState.error != null && uiState.transactions.isEmpty()) {
        Text(
            text = "Something went wrong",
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = uiState.error,
            modifier = Modifier.padding(top = 8.dp)
        )

    } else if (uiState.isLoading && uiState.transactions.isEmpty()) {
        Text(
            text = "Loading Transactions..,",
            modifier = Modifier.padding(16.dp)
        )
    } else if (uiState.transactions.isEmpty()) {
        Text(
            text = "No transactions yet",
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = "Your transactions will appear here."
        )
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = uiState.transactions,
                key = { transaction -> transaction.id }
            ) { transaction ->
                TransactionCard(transaction)
            }
        }
    }
}

@Composable
private fun TransactionCard(
    transaction: Transaction
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = transaction.merchant,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "Rs. ${transaction.amount}",
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = transaction.category.name,
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = transaction.date,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
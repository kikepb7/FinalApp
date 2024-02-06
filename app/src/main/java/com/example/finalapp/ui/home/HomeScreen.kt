package com.example.finalapp.ui.home

import android.service.autofill.UserData
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.finalapp.R
import com.example.finalapp.ui.components.AppAlertDialog
import com.example.finalapp.ui.components.AppDivider
import com.example.finalapp.ui.components.formatAmount
import java.util.Locale

private val AppDefaultPadding = 12.dp

@Composable
fun HomeScreen(

) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .semantics { contentDescription = "Home Screen" }
    ) {
        AlertCard()
        Spacer(Modifier.height(AppDefaultPadding))
        AccountsCard(
            onClickSeeAll = onClickSeeAllAccounts,
            onAccountClick = onAccountClick
        )
        Spacer(Modifier.height(AppDefaultPadding))

    }
}

@Composable
fun AlertCard() {
    var showDialog by remember { mutableStateOf(false) }
    val alertMessage = "Has usado el 90% de tu presupuesto este mes"

    if (showDialog) {
        AppAlertDialog(
            onDismiss = {
                showDialog = false
            },
            bodyText = alertMessage,
            buttonText = "Dismiss".uppercase(Locale.getDefault())
        )
    }
    Card {
        Column {
            AlertHeader {
                showDialog = true
            }
            AppDivider(
                modifier = Modifier
                    .padding(start = AppDefaultPadding, end = AppDefaultPadding)
            )
            AlertItem(alertMessage)
        }
    }
}

@Composable
private fun AlertHeader(
    onClickSeeAll: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(AppDefaultPadding)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Alertas",
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        TextButton(
            onClick = { onClickSeeAll },
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Text(
                text = "VER TODO",
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Composable
private fun AlertItem(message: String) {
    Row(
        modifier = Modifier
            .padding(AppDefaultPadding)
            .semantics(mergeDescendants = true) {},
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = message,
            modifier = Modifier
                .align(Alignment.Top)
                .clearAndSetSemantics {},
            style = MaterialTheme.typography.bodySmall
        )
        IconButton(
            onClick = {},
            modifier = Modifier
                .align(Alignment.Top)
                .clearAndSetSemantics {}
        ) {
            Icon(Icons.Filled.Menu, contentDescription = null)
        }
    }
}

@Composable
private fun AccountsCard(
    onClickSeeAll: () -> Unit,
    onAccountClick: (String) -> Unit
) {
    HomeScreenCard(
        title = stringResource(R.string.accounts),
        amount = amount,
        onClickSeeAll = onClickSeeAll,
        data = UserData.accounts,
        colors = { it.color },
        values = { it.balance }
    ) { account ->
        AccountRow(
            modifier = Modifier.clickable { onAccountClick(account.name) },
            name = account.name,
            number = account.number,
            amount = account.balance,
            color = account.color
        )
    }
}

@Composable
private fun <T> HomeScreenCard(
    title: String,
    amount: Float,
    onClickSeeAll: () -> Unit,
    values: (T) -> Float,
    colors: (T) -> Color,
    data: List<T>,
    row: @Composable (T) -> Unit
) {
    Card {
        Column(
            Modifier.padding(AppDefaultPadding)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall
            )
            val amountText = "€" + formatAmount(
                amount
            )
            Text(text = amountText, style = MaterialTheme.typography.bodyMedium)
        }
        HomeDivider(data, values, colors)
        Column(
            Modifier.padding(start = 16.dp, top = 4.dp, end = 8.dp)
        ) {
            data.take(SHOWN_ITEMS).forEach { row(it) }
            SeeAllButton(
                modifier = Modifier.clearAndSetSemantics {
                    contentDescription = "Todo $title"
                },
                onClick = onClickSeeAll
            )
        }
    }
}

@Composable
private fun <T> HomeDivider(
    data: List<T>,
    values: (T) -> Float,
    colors: (T) -> Color
) {
    Row(Modifier.fillMaxWidth()) {
        data.forEach { item: T ->
            Spacer(
                modifier = Modifier
                    .weight(values(item))
                    .height(1.dp)
                    .background(colors(item))
            )
        }
    }
}

@Composable
private fun SeeAllButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    TextButton(
        onClick = onClick,
        modifier = modifier
            .height(44.dp)
            .fillMaxWidth()
    ) {
        Text(stringResource(R.string.see_all))
    }
}


@Composable
@Preview(showBackground = true)
fun HomeScreenPreview() {
    HomeScreen()
}
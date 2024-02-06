package com.example.finalapp.ui.components

import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.text.DecimalFormat

@Composable
fun AppDivider(modifier: Modifier = Modifier) {
    Divider(
        color = MaterialTheme.colorScheme.background,
        thickness = 1.dp,
        modifier = modifier
    )
}

fun formatAmount(amount: Float): String {
    return AmountDecimalFormat.format(amount)
}

private val AmountDecimalFormat = DecimalFormat("#,###,##")
package com.merp.jet.book.practical.app.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun VerticalSpace(value: Int = 20) {
    Spacer(modifier = Modifier.height(value.dp))
}
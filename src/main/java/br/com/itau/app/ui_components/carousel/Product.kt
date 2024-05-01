package br.com.itau.app.ui_components.carousel

import androidx.compose.ui.graphics.Color

data class Product(
    val productName: String,
    val topLeftText: String,
    val balanceText: String,
    val percentColor: Color,
    val click: (product: Product) -> Unit
)
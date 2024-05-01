package br.com.itau.app.ui_components.graph

import androidx.compose.ui.graphics.Color


data class PieChartInput(
    val color:Color,
    val value:Int,
    val description:String,
    val isTapped:Boolean = false
)
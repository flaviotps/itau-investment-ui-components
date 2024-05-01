package br.com.itau.app.ui_components.text

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.itau.app.ui_components.R
import br.com.itau.app.ui_components.commons.format
import br.com.itau.app.ui_components.theme.Component
import kotlinx.coroutines.delay
import kotlin.random.Random

/**
 * Composable function to display stock text with an optional arrow icon indicating the direction of change.
 *
 * @param modifier The modifier to be applied to the stock text.
 * @param value The value of the stock.
 * @param textSize The size of the text.
 * @param imageSize The size of the arrow icon.
 * @param showArrow Whether to show the arrow icon.
 */
@Composable
fun StockText(
    modifier: Modifier = Modifier,
    value: Float,
    textSize: TextUnit = 16.sp,
    imageSize: Dp = 12.dp,
    showArrow: Boolean = true
) {
    var walletValue by remember { mutableFloatStateOf(value) }
    var direction by remember { mutableStateOf(StockDirection.NONE) }

    var textColor by remember { mutableStateOf(Color.LightGray) }

    LaunchedEffect(walletValue) {
        delay(1000)
        walletValue += Random.nextFloat() * 0.4f - 0.2f
    }

    direction = when {
        walletValue > 0 -> StockDirection.UP
        walletValue < 0 -> StockDirection.DOWN
        else -> StockDirection.NONE
    }

    textColor = when (direction) {
        StockDirection.UP -> Component.Color.lightGreen
        StockDirection.DOWN -> Component.Color.red
        StockDirection.NONE -> Color.LightGray
    }

    val rotationDegrees by animateFloatAsState(
        targetValue = if (direction == StockDirection.UP) 0f else 180f,
        label = "Stock Arrow Rotation"
    )

    val icon = painterResource(R.drawable.stock_arrow)

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (showArrow) {
            Image(
                painter = icon,
                contentDescription = "Stock Arrow",
                modifier = Modifier
                    .height(imageSize)
                    .width(imageSize)
                    .rotate(rotationDegrees),
                colorFilter = ColorFilter.tint(textColor)
            )
        }
        Text(
            text = "${walletValue.format()}%",
            color = textColor,
            fontSize = textSize,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

private enum class StockDirection {
    UP,
    DOWN,
    NONE
}

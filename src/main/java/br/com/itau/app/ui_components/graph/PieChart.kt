package br.com.itau.app.ui_components.graph

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.itau.app.ui_components.commons.format
import br.com.itau.app.ui_components.commons.formatCurrency
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.hypot


internal const val DEFAULT_PRODUCT = "Todos os produtos"
internal const val DEFAULT_PRODUCT_PERCENTAGE = 100f

/**
 * Composable function to draw a pie chart with interactive features.
 *
 * @param modifier The modifier to be applied to the pie chart.
 * @param radius The radius of the pie chart.
 * @param innerRadius The inner radius of the pie chart.
 * @param transparentWidth The width of the transparent part of the chart.
 * @param input The list of input data for the pie chart.
 */
@Composable
fun PieChart(
    modifier: Modifier = Modifier,
    radius: Float = 500f,
    innerRadius: Float = 250f,
    transparentWidth: Float = 70f,
    input: List<PieChartInput>
) {
    var circleCenter by remember { mutableStateOf(Offset.Zero) }
    var inputList by remember { mutableStateOf(input) }
    var isCenterTapped by remember { mutableStateOf(false) }

    val totalValue = input.sumOf { it.value }

    var percentageText by remember { mutableStateOf("${DEFAULT_PRODUCT_PERCENTAGE}%") }
    var productValue by remember { mutableFloatStateOf(totalValue.toFloat()) }
    var productName by remember { mutableStateOf(DEFAULT_PRODUCT) }

    if (isCenterTapped) {
        percentageText = "${DEFAULT_PRODUCT_PERCENTAGE}%"
        productValue = totalValue.toFloat()
        productName = DEFAULT_PRODUCT
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(true) {
                    detectTapGestures(
                        onTap = { offset ->
                            val distance =
                                hypot(offset.x - circleCenter.x, offset.y - circleCenter.y)

                            if (distance <= innerRadius) {
                                inputList = inputList.map {
                                    it.copy(isTapped = !isCenterTapped)
                                }
                                isCenterTapped = !isCenterTapped
                            } else {
                                val tapAngleInDegrees = (-atan2(
                                    x = circleCenter.y - offset.y,
                                    y = circleCenter.x - offset.x
                                ) * (180f / PI).toFloat() - 90f).mod(360f)

                                val anglePerValue = 360f / input.sumOf { it.value }
                                var currAngle = 0f

                                inputList.forEach { pieChartInput ->
                                    currAngle += pieChartInput.value * anglePerValue

                                    if (tapAngleInDegrees < currAngle) {
                                        val percentageValue =
                                            pieChartInput.value.toFloat() / totalValue.toFloat() * 100
                                        percentageText = "${percentageValue.format()}%"
                                        productValue = pieChartInput.value.toFloat()
                                        productName = pieChartInput.description

                                        inputList = inputList.map { it ->
                                            if (it.description == pieChartInput.description) {
                                                it.copy(isTapped = !it.isTapped)
                                            } else {
                                                it.copy(isTapped = false)
                                            }
                                        }
                                        return@detectTapGestures
                                    }
                                }
                            }
                        }
                    )
                }
        ) {
            val width = size.width
            val height = size.height
            circleCenter = Offset(x = width / 2f, y = height / 2f)

            val anglePerValue = 360f / totalValue
            var currentStartAngle = 0f

            inputList.forEach { pieChartInput ->
                val scale = if (pieChartInput.isTapped) 1.1f else 1.0f
                val angleToDraw = pieChartInput.value * anglePerValue

                scale(scale) {
                    drawArc(
                        color = pieChartInput.color,
                        startAngle = currentStartAngle,
                        sweepAngle = angleToDraw,
                        useCenter = true,
                        size = Size(
                            width = radius * 2f,
                            height = radius * 2f
                        ),
                        topLeft = Offset(
                            x = (width - radius * 2f) / 2f,
                            y = (height - radius * 2f) / 2f
                        )
                    )
                    currentStartAngle += angleToDraw
                }

                var rotateAngle = currentStartAngle - angleToDraw / 2f - 90f
                var factor = 1f

                if (rotateAngle > 90f) {
                    rotateAngle = (rotateAngle + 180).mod(360f)
                    factor = -0.92f
                }

                val percentage = (pieChartInput.value / totalValue.toFloat() * 100).toInt()

                drawContext.canvas.nativeCanvas.apply {
                    if (percentage > 3) {
                        rotate(rotateAngle) {
                            drawText(
                                "$percentage %",
                                circleCenter.x,
                                circleCenter.y + (radius - (radius - innerRadius) / 2f) * factor,
                                Paint().apply {
                                    textSize = 13.sp.toPx()
                                    textAlign = Paint.Align.CENTER
                                    color = Color.White.toArgb()
                                }
                            )
                        }
                    }
                }

                drawPieDivider(currentStartAngle, angleToDraw, circleCenter, radius)

                if (pieChartInput.isTapped) {
                    val tabRotation = currentStartAngle - angleToDraw - 90f
                    rotate(tabRotation) {
                        drawRoundRect(
                            topLeft = circleCenter,
                            size = Size(12f, radius * 1.1f),
                            color = Color.White,
                            cornerRadius = CornerRadius(15f, 15f)
                        )
                    }
                    rotate(tabRotation + angleToDraw) {
                        drawRoundRect(
                            topLeft = circleCenter,
                            size = Size(12f, radius * 1.1f),
                            color = Color.White,
                            cornerRadius = CornerRadius(15f, 15f)
                        )
                    }
                    rotate(rotateAngle) {
                        drawContext.canvas.nativeCanvas.apply {
                            drawText(
                                pieChartInput.description,
                                circleCenter.x,
                                circleCenter.y + radius * 1.3f * factor,
                                Paint().apply {
                                    textSize = 12.sp.toPx()
                                    textAlign = Paint.Align.CENTER
                                    color = Color.Black.toArgb()
                                    isFakeBoldText = false
                                }
                            )
                        }
                    }
                }
            }
            drawContext.canvas.nativeCanvas.apply {
                drawCircle(
                    circleCenter.x,
                    circleCenter.y,
                    innerRadius,
                    Paint().apply {
                        color = Color.White.copy(alpha = 1f).toArgb()
                        setShadowLayer(10f, 0f, 0f, Color.Gray.toArgb())
                    }
                )
            }

            drawCircle(
                color = Color.White.copy(alpha = 0.2f),
                radius = innerRadius + transparentWidth / 2f
            )
        }

        Column(
            modifier = Modifier.width((innerRadius / 1.5f).dp)
        ) {
            Text(
                text = percentageText,
                modifier = Modifier.width((innerRadius / 1.5f).dp),
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )
            Text(
                text = productName,
                modifier = Modifier.width((innerRadius / 1.5f).dp),
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
            Text(
                text = productValue.formatCurrency(),
                modifier = Modifier.width((innerRadius / 1.5f).dp),
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

/**
 * Draws the divider lines between pie slices.
 */
private fun DrawScope.drawPieDivider(
    currentStartAngle: Float,
    angleToDraw: Float,
    circleCenter: Offset,
    radius: Float
) {
    val tabRotation = currentStartAngle - angleToDraw - 90f

    rotate(tabRotation) {
        drawRoundRect(
            topLeft = circleCenter,
            size = Size(6f, radius * 1f),
            color = Color.White,
            cornerRadius = CornerRadius(15f, 15f)
        )
    }

    rotate(tabRotation + angleToDraw) {
        drawRoundRect(
            topLeft = circleCenter,
            size = Size(6f, radius * 1f),
            color = Color.White,
            cornerRadius = CornerRadius(15f, 15f)
        )
    }
}

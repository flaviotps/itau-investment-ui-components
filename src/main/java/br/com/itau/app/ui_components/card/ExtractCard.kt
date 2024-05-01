package br.com.itau.app.ui_components.card

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.itau.app.ui_components.R
import br.com.itau.app.ui_components.commons.formatCurrency
import kotlin.random.Random


/**
 * A Composable component that represents a card displaying extract information.
 *
 * @param modifier The modifier to be applied to the card.
 * @param tbi The unique identifier for the extract transaction.
 * @param value The value of the stock.
 */
@Composable
fun ExtractCard(
    modifier: Modifier = Modifier,
    tbi: String = generateId(),
    value : Float = getRandomFloat()
) {

    val icon = painterResource(R.drawable.stock_arrow)

    val rotationDegrees by animateFloatAsState(
        targetValue = if (value > 0) 0f else 180f,
        label = stringResource(R.string.stock_arrow_rotation)
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(0.dp),
        elevation = 0.dp,
        backgroundColor = Color.White
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = icon,
                contentDescription = stringResource(R.string.stock_arrow),
                modifier = Modifier
                    .height(16.dp)
                    .width(16.dp)
                    .rotate(rotationDegrees)
            )
            Text(
                text = tbi,
                fontSize = 16.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = value.formatCurrency(),
                fontSize = 14.sp,
                color = Color.Black
            )
        }
    }
}

private fun generateId(): String {
    val randomNumber = Random.nextInt(10)
    val firstFourDigits = Random.nextInt(10000).toString().padStart(4, '0')
    val secondFourDigits = Random.nextInt(10000).toString().padStart(4, '0')
    return "tbi.$firstFourDigits.$secondFourDigits-$randomNumber"
}

private fun getRandomFloat(): Float {
    return Random.nextFloat() * 200 - 100
}

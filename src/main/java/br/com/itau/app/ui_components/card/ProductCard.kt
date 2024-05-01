package br.com.itau.app.ui_components.card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import br.com.itau.app.ui_components.R
import br.com.itau.app.ui_components.carousel.Product
import br.com.itau.app.ui_components.theme.Component.Color.middleGray

/**
 * Composable function to display a product card.
 *
 * @param modifier Modifier to be applied to the card.
 * @param product Product data to be displayed.
 */
@Composable
fun ProductCard(
    modifier: Modifier = Modifier,
    product: Product
) {
    Card(
        elevation = 8.dp,
        border = BorderStroke(1.dp, Color.LightGray),
        modifier = modifier
            .fillMaxSize()
            .clickable {
                product.click(product)
            }
    ) {
        Column(
            modifier = Modifier
                .padding(all = 8.dp)
                .fillMaxSize()
        ) {
            Row {
                Text(
                    modifier = Modifier.background(product.percentColor),
                    text = product.topLeftText,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                Text(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Start,
                    text = product.productName,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.SemiBold,
                )
            }
            Column {
                Row(
                    modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.investment_total),
                        color = middleGray,
                        textAlign = TextAlign.Start
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.Gray,
                        textAlign = TextAlign.End,
                        text = product.balanceText
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        color = Color.Black,
                        textAlign = TextAlign.Start,
                        text = stringResource(R.string.go_to_product)
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = stringResource(R.string.go_to_product),
                        tint = Color.Black
                    )
                }
            }
        }
    }
}


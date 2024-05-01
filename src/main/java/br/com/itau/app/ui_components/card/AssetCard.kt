package br.com.itau.app.ui_components.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import br.com.itau.app.ui_components.R
import br.com.itau.app.ui_components.commons.formatCurrency

/**
 * Composable function to display an asset card.
 *
 * @param modifier Modifier to be applied to the card.
 * @param name Name of the asset.
 * @param quantity Quantity of the asset.
 * @param unitPrice Unit price of the asset.
 * @param titleColor Color of the asset name.
 * @param titleBackgroundColor Background color of the asset name.
 */
@Composable
fun AssetCard(
    modifier: Modifier = Modifier,
    name: String,
    quantity: Float,
    unitPrice: Double,
    titleColor: Color = Color.Black,
    titleBackgroundColor: Color = Color.Transparent
) {
    val total = (quantity * unitPrice).formatCurrency()

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = 8.dp,
        backgroundColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .background(titleBackgroundColor),
                color = titleColor,
                text = name,
                style = MaterialTheme.typography.h5,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Divider(thickness = 2.dp)

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.stocks_quantity, quantity),
                style = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.unit_price, unitPrice.formatCurrency()),
                style = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.total, total),
                style = MaterialTheme.typography.body1
            )
        }
    }
}

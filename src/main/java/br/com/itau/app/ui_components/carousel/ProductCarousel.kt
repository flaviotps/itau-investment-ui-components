import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.itau.app.ui_components.card.ProductCard
import br.com.itau.app.ui_components.carousel.Product

/**
 * Composable function to display a carousel of products.
 *
 * @param modifier Modifier to be applied to the carousel.
 * @param products List of products to be displayed in the carousel.
 */
@Composable
fun ProductCarousel(
    modifier: Modifier = Modifier,
    products: List<Product>
) {
    var currentIndex by remember { mutableIntStateOf(0) }

    Row(
        modifier = modifier
            .fillMaxSize()
            .horizontalScroll(rememberScrollState())
            .padding(vertical = 16.dp)
    ) {
        products.forEachIndexed { index, product ->
            ProductCard(
                product = product,
                modifier = Modifier.width(250.dp)
                    .padding(horizontal = 8.dp)
                    .clickable { currentIndex = index }
            )
        }
    }
}

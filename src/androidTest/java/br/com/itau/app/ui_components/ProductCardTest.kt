package br.com.itau.app.ui_components

import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import br.com.itau.app.ui_components.card.ProductCard
import br.com.itau.app.ui_components.carousel.Product
import org.junit.Rule
import org.junit.Test

class ProductCardTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun productCard_LayoutAndContent() {
        val product = Product(
            productName = "Fundos de investimento",
            topLeftText = "52%",
            balanceText = "R$ 7.200,00",
            percentColor = Color.Green,
            click = { }
        )

        composeTestRule.setContent {
            ProductCard(
                modifier = Modifier.size(width = 220.dp, height = 100.dp),
                product = product
            )
        }

        composeTestRule.onNodeWithText("Fundos de investimento").assertExists()

        composeTestRule.onNodeWithText("52%").assertExists()

        composeTestRule.onNodeWithText("R$ 7.200,00").assertExists()

        composeTestRule.onNodeWithText("52%").assertBackgroundColor(Color.Green)
    }

    @Test
    fun productCard_ClickAction() {
        var clicked = false
        val product = Product(
            productName = "Fundos de investimento",
            topLeftText = "52%",
            balanceText = "R$ 7.200,00",
            percentColor = Color.Green,
            click = { clicked = true }
        )

        composeTestRule.setContent {
            ProductCard(
                modifier = Modifier.size(width = 220.dp, height = 100.dp),
                product = product
            )
        }


        composeTestRule.onNodeWithText("Fundos de investimento").performClick()

        assert(clicked)
    }
}

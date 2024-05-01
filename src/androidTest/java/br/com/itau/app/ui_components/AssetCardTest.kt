package br.com.itau.app.ui_components

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import br.com.itau.app.ui_components.card.AssetCard
import br.com.itau.app.ui_components.commons.formatCurrency
import br.com.itau.app.ui_components.theme.ItauInvestmentsTheme
import org.junit.Rule
import org.junit.Test

class AssetCardTest {


    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun assetCard_ContentDisplayed() {
        val name = "Apple"
        val quantity = 10f
        val unitPrice = 2.5
        val total = (quantity * unitPrice).formatCurrency()

        composeTestRule.setContent {
            AssetCard(
                name = name,
                quantity = quantity,
                unitPrice = unitPrice
            )
        }

        composeTestRule.onNodeWithText(name).assertIsDisplayed()
        composeTestRule.onNodeWithText("Quantidade: $quantity").assertIsDisplayed()
        composeTestRule.onNodeWithText("Preço unitário: ${unitPrice.formatCurrency()}").assertIsDisplayed()
        composeTestRule.onNodeWithText("Posição: $total").assertIsDisplayed()
    }

    @Test
    fun assetCard_TitleColors() {
        val name = "Apple"
        val titleColor = Color.Red
        val titleBackgroundColor = Color.Blue

        composeTestRule.setContent {
            ItauInvestmentsTheme {
                AssetCard(
                    name = name,
                    quantity = 10f,
                    unitPrice = 2.5,
                    titleColor = titleColor,
                    titleBackgroundColor = titleBackgroundColor
                )
            }
        }

        composeTestRule.onNodeWithText(name).assertTextColor(titleColor)
        composeTestRule.onNodeWithText(name).assertBackgroundColor(titleBackgroundColor)
    }
}


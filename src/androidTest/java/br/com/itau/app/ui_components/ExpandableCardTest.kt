package br.com.itau.app.ui_components

import ExpandableCard
import androidx.compose.material.Text
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class ExpandableCardTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun expandableCard_InitialState_Collapsed() {

        composeTestRule.setContent {
            ExpandableCard(
                initialState = false,
                titleContent = { Text(text = "Title") },
                content = { Text(text = "Content") }
            )
        }
        composeTestRule.onNodeWithText("Content").assertDoesNotExist()
    }

    @Test
    fun expandableCard_InitialState_Expanded() {
        composeTestRule.setContent {
            ExpandableCard(
                initialState = true,
                titleContent = { Text(text = "Title") },
                content = { Text(text = "Content") }
            )
        }

        composeTestRule.onNodeWithText("Content").assertIsDisplayed()
    }

    @Test
    fun expandableCard_ClickToExpandAndCollapse() {

        composeTestRule.setContent {
            ExpandableCard(
                initialState = false,
                titleContent = { Text(text = "Title") },
                content = { Text(text = "Content") }
            )
        }

        composeTestRule.onNodeWithContentDescription("Collapse").performClick()

        composeTestRule.onNodeWithText("Content").assertIsDisplayed()

        composeTestRule.onNodeWithContentDescription("Collapse").performClick()

        composeTestRule.onNodeWithText("Content").assertDoesNotExist()
    }
}

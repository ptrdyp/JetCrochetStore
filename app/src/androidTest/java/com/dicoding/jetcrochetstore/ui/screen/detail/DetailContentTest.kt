package com.dicoding.jetcrochetstore.ui.screen.detail

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import com.dicoding.jetcrochetstore.R
import com.dicoding.jetcrochetstore.model.Crochet
import com.dicoding.jetcrochetstore.model.OrderCrochet
import com.dicoding.jetcrochetstore.onNodeWithStringId
import com.dicoding.jetcrochetstore.ui.theme.JetCrochetStoreTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailContentTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val fakeOrderCrochet = OrderCrochet(
        crochet = Crochet(
            1,
            "https://i.pinimg.com/564x/2e/0b/d2/2e0bd2052cf52f96d79fc61a0346197f.jpg",
            "Cozy Comfort Blanket",
            "Quilt",
            "Made with high quality yarn to provide extra comfort at night.",
            300000,
        ),
        count = 0
    )

    @Before
    fun setUp() {
        composeTestRule.setContent {
            JetCrochetStoreTheme {
                DetailContent(
                    image = fakeOrderCrochet.crochet.image,
                    title = fakeOrderCrochet.crochet.title,
                    category = fakeOrderCrochet.crochet.category,
                    description = fakeOrderCrochet.crochet.description,
                    basePrice = fakeOrderCrochet.crochet.price,
                    count = fakeOrderCrochet.count,
                    onBackClick = {},
                    onAddToCart = {}
                )
            }
        }
        composeTestRule.onRoot().printToLog("currentLabelExists")
    }

    @Test
    fun detailContent_isDisplayed() {
        composeTestRule.onNodeWithText(fakeOrderCrochet.crochet.title).assertIsDisplayed()
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(
                R.string.price,
                fakeOrderCrochet.crochet.price
            )
        ).assertIsDisplayed()
    }

    @Test
    fun increaseProduct_buttonEnabled() {
        composeTestRule.onNodeWithContentDescription("Order Button").assertIsNotEnabled()
        composeTestRule.onNodeWithStringId(R.string.plus_symbol).performClick()
        composeTestRule.onNodeWithContentDescription("Order Button").assertIsEnabled()
    }

    @Test
    fun increaseProduct_correctCounter() {
        composeTestRule.onNodeWithStringId(R.string.plus_symbol).performClick().performClick()
        composeTestRule.onNodeWithTag("count").assert(hasText("2"))
    }

    @Test
    fun decreaseProduct_stillZero() {
        composeTestRule.onNodeWithStringId(R.string.minus_symbol).performClick()
        composeTestRule.onNodeWithTag("count").assert(hasText("0"))
    }
}
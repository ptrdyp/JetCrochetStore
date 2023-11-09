package com.dicoding.jetcrochetstore

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.dicoding.jetcrochetstore.navigation.Screen
import com.dicoding.jetcrochetstore.ui.theme.JetCrochetStoreTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class JetCrochetStoreAppTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        composeTestRule.setContent {
            JetCrochetStoreTheme {
                navController = TestNavHostController(LocalContext.current)
                navController.navigatorProvider.addNavigator(ComposeNavigator())
                JetCrochetStoreApp(navController = navController)
            }
        }
        composeTestRule.waitForIdle()
    }

    @Test
    fun navHost_verifyStartDestination() {
        navController.assertCurrentRouteName(Screen.Home.route)
    }
}
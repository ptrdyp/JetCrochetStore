package com.dicoding.jetcrochetstore.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Cart : Screen("cart")
    object About : Screen("about")
    object DetailCrochet : Screen("home/{crochetId}") {
        fun createRoute(crochetId: Long) = "home/$crochetId"
    }
}
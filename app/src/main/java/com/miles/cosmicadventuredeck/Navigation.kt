package com.miles.cosmicadventuredeck

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.miles.cosmicadventuredeck.ui.CardScreen
import com.miles.cosmicadventuredeck.ui.WelcomeScreen

enum class Screen {
    WELCOME,
    CARD
}

sealed class NavigationItem(val route: String) {
    data object Main : NavigationItem(Screen.WELCOME.name)
    data object Card : NavigationItem(Screen.CARD.name)
}

@Composable
fun DMTarotDeckApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = NavigationItem.Main.route
) {
    Scaffold { innerPadding ->
        NavHost(
            modifier = modifier.padding(innerPadding),
            navController = navController,
            startDestination = startDestination
        ) {
            composable(route = NavigationItem.Main.route) {
                WelcomeScreen(
                    onNavigateToCardScreen = {
                        navController.navigate(NavigationItem.Card.route)
                    }
                )
            }
            composable(
                route = NavigationItem.Card.route,
                enterTransition = {
                    fadeIn(
                        animationSpec = tween(
                            800,
                            300,
                            easing = LinearEasing
                        )
                    )
                }) {
                CardScreen(
                    onBackClick = {
                        navController.navigate(
                            route = NavigationItem.Main.route
                        )
                    })
            }
        }
    }
}


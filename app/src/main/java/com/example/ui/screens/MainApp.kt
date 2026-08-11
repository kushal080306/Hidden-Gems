package com.example.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.model.UserProfile

sealed class Screen(val route: String, val title: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    object Home : Screen("home", "Home", Icons.Default.Home)
    object AI : Screen("ai", "AI Assist", Icons.Default.SmartToy)
    object Profile : Screen("profile", "Profile", Icons.Default.Person)
}

@Composable
fun MainApp(user: UserProfile, onSignOut: () -> Unit) {
    val navController = rememberNavController()
    val items = listOf(Screen.Home, Screen.AI, Screen.Profile)

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(onNavigateToDetail = { placeId ->
                    navController.navigate("detail/$placeId")
                })
            }
            composable("detail/{placeId}") { backStackEntry ->
                val placeId = backStackEntry.arguments?.getString("placeId")
                if (placeId != null) {
                    DetailScreen(placeId = placeId, onNavigateBack = { navController.popBackStack() })
                }
            }
            composable(Screen.AI.route) {
                AiAssistantScreen()
            }
            composable(Screen.Profile.route) {
                ProfileScreen(user = user, onSignOut = onSignOut)
            }
        }
    }
}

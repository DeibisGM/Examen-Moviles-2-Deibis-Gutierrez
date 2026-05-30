package com.app.copamundialfifa2026.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.app.copamundialfifa2026.core.FeatureFlags
import com.app.copamundialfifa2026.data.AuthSession
import com.app.copamundialfifa2026.ui.screens.createticket.CreateTicketScreen
import com.app.copamundialfifa2026.ui.screens.login.LoginScreen
import com.app.copamundialfifa2026.ui.screens.ticketdetail.TicketDetailScreen
import com.app.copamundialfifa2026.ui.screens.ticketlist.TicketListScreen

/** Single navigation graph: Login → Ticket List → (Detail | Create). */
@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppDestinations.LOGIN
    ) {
        composable(AppDestinations.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(AppDestinations.TICKET_LIST) {
                        popUpTo(AppDestinations.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        composable(AppDestinations.TICKET_LIST) {
            TicketListScreen(
                onTicketClick = { id -> navController.navigate(AppDestinations.ticketDetailRoute(id)) },
                onCreateClick = { navController.navigate(AppDestinations.CREATE_TICKET) },
                onLogout = {
                    AuthSession.clear()
                    navController.navigate(AppDestinations.LOGIN) {
                        popUpTo(AppDestinations.TICKET_LIST) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = AppDestinations.TICKET_DETAIL,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id").orEmpty()
            TicketDetailScreen(
                ticketId = id,
                onBack = { navController.popBackStack() }
            )
        }

        composable(AppDestinations.CREATE_TICKET) {
            if (!FeatureFlags.CREATE_TICKET_ENABLED) {
                LaunchedEffect(Unit) { navController.popBackStack() }
                return@composable
            }
            CreateTicketScreen(
                onBack = { navController.popBackStack() },
                onCreated = { navController.popBackStack() }
            )
        }
    }
}

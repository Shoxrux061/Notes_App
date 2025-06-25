package uz.shoxrux.notesapp.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import uz.shoxrux.core.utils.NavRoutes
import uz.shoxrux.presentation.screens.detail.DetailViewModel
import uz.shoxrux.presentation.screens.detail.DetailsScreen
import uz.shoxrux.presentation.screens.home.HomeScreen
import uz.shoxrux.presentation.screens.home.HomeViewModel
import uz.shoxrux.presentation.ui.color.LocalAppColors

@Composable
fun AppNavHost(paddingValues: PaddingValues, snackbarHostState: SnackbarHostState) {

    val navController = rememberNavController()
    val viewModel = hiltViewModel<HomeViewModel>()
    val detailViewModel = hiltViewModel<DetailViewModel>()

    NavHost(
        modifier = Modifier
            .background(LocalAppColors.current.background)
            .padding(paddingValues),
        navController = navController,
        startDestination = NavRoutes.HOME_SCREEN
    ) {

        composable(NavRoutes.HOME_SCREEN) {
            HomeScreen(viewModel, navController, snackbarHostState)
        }

        composable(
            route = NavRoutes.DETAIL_SCREEN + "/{noteId}",
            arguments = listOf(
                navArgument("noteId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getInt("noteId") ?: -1
            DetailsScreen(navController, detailViewModel, noteId)
        }
    }
}
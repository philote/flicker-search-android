package com.josephhopson.flickersearch.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.josephhopson.flickersearch.ui.home.HomeDestination
import com.josephhopson.flickersearch.ui.home.HomeScreen
import com.josephhopson.flickersearch.ui.image.ImageDetailsDestination
import com.josephhopson.flickersearch.ui.image.ImageDetailsScreen

@Composable
fun FlickerSearchNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToItemDetail = {
                    navController.navigate("${ImageDetailsDestination.route}/${it}")
                }
            )
        }
        composable(
            route = ImageDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(ImageDetailsDestination.IMAGE_ID_ARG){
                type = NavType.IntType
            }),
        ) {
            ImageDetailsScreen(
                navigateBack = { navController.navigateUp() }
            )
        }
    }
}
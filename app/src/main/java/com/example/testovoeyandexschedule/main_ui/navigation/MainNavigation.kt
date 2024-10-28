package com.example.testovoeyandexschedule.main_ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testovoeyandexschedule.di.ControllersModule
import com.example.testovoeyandexschedule.main_ui.MainMenu
import com.example.testovoeyandexschedule.main_ui.OrderDetail
import com.example.testovoeyandexschedule.main_ui.SearchMenu

@Composable
fun MainNavigation(controllers: ControllersModule)
{
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.MainScreen.route)
    {
        composable(Routes.MainScreen.route)
        {
            MainMenu(navController,controllers.provideMainMenuController(),controllers.provideSelectedText())
        }
        composable(Routes.SearchScreen.route)
        {
            SearchMenu(navController,controllers.provideMainMenuController(),controllers.provideSelectedText())
        }
        composable(Routes.OrderDetailScreen.route)
        {
            OrderDetail(navController)
        }
    }

}
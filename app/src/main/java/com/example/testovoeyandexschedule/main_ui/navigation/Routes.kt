package com.example.testovoeyandexschedule.main_ui.navigation

object Routes
{
    object MainScreen : Screen ("main_screen")

    object SearchScreen : Screen ("search_screen")

    object OrderDetailScreen : Screen ("orderDetail_screen")

}

sealed class Screen(val route : String)
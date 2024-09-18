package com.example.testovoeyandexschedule

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.testovoeyandexschedule.di.MyApp
import com.example.testovoeyandexschedule.main_ui.navigation.MainNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val coreSupervisor = (application as MyApp).cs

            val controllers = with(coreSupervisor.ApplicationMain())
            {
                getAppControllers()
            }
            MainNavigation(controllers)
        }
    }
}

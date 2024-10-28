package com.example.testovoeyandexschedule

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.testovoeyandexschedule.di.MyApp
import com.example.testovoeyandexschedule.main_ui.navigation.MainNavigation
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.osmdroid.config.Configuration

class MainActivity : ComponentActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        val coreSupervisor = (application as MyApp).cs

        val controllers = with(coreSupervisor.ApplicationMain())
        {
            getAppControllers()
        }
        setContent {
            MainNavigation(controllers)
        }
    }
}

package com.example.testovoeyandexschedule.main_ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.testovoeyandexschedule.R
import com.example.testovoeyandexschedule.ui.theme.MainMenuBtn

object ErrorsScreens {

    @Composable
    fun LoadingBadge()
    {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                Modifier.size(150.dp),
                color = MainMenuBtn
            )
        }
    }

    @Composable
    fun BadRequest()
    {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center)
        {
            Row {
                Icon(imageVector = Icons.Filled.SearchOff , contentDescription ="")
                Text(stringResource(id = R.string.bad_request_text))

            }
        }

    }

    @Composable
    fun ConnectionError()
    {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center)
        {
            Row {
                Icon(imageVector = Icons.Filled.WifiOff , contentDescription ="")
                Text(stringResource(id = R.string.connection_error_text))

            }
        }
    }
}
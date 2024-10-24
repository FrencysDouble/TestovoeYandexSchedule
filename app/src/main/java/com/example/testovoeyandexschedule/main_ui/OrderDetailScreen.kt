package com.example.testovoeyandexschedule.main_ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.testovoeyandexschedule.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun OrderDetail()
{
    Scaffold(Modifier.fillMaxSize()) {
        Screen()
    }
}

@Composable
private fun Screen()
{

}

@Composable
@Preview
private fun MainDetail()
{
    Box(Modifier.fillMaxWidth().background(Color.White).clip(RoundedCornerShape(12.dp)))
    {
        Row() {
            Icon(painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = "",
                Modifier.clip(RoundedCornerShape(12.dp)).size(36.dp))
            Column {
                Text("Аэрофлот")
                Text("1ч 35м в полете")
            }
        }

        Row() {
            TwoPointersItem()
            
        }
    }
}

@Composable
@Preview
private fun TwoPointersItem()
{
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(Modifier.size(24.dp).clip(RoundedCornerShape(64.dp)).background(Color.Gray), contentAlignment = Alignment.Center)
        {
            Box(Modifier.size(12.dp).clip(RoundedCornerShape(64.dp)).background(Color.White))
        }
        VerticalDivider(Modifier.background(Color.Gray).height(32.dp)
            .width(1.dp))
        Box(Modifier.size(24.dp).clip(RoundedCornerShape(64.dp)).background(Color.Gray), contentAlignment = Alignment.Center)
        {
            Box(Modifier.size(12.dp).clip(RoundedCornerShape(64.dp)).background(Color.White))
        }
    }

}

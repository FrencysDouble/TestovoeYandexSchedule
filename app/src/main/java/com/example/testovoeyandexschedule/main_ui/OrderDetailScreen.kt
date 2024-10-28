package com.example.testovoeyandexschedule.main_ui

import android.annotation.SuppressLint
import android.widget.Button
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.example.testovoeyandexschedule.R
import com.example.testovoeyandexschedule.ui.theme.BackroundMain
import com.example.testovoeyandexschedule.ui.theme.MainMenuBtn
import com.example.testovoeyandexschedule.ui.theme.TextSecondary
import com.example.testovoeyandexschedule.ui.theme.TextTertiary
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.MapView

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun OrderDetail(navController: NavHostController)
{
    Scaffold(Modifier.fillMaxSize()) {
        Screen()
    }
}

@Composable
@Preview
private fun Screen() {
    Column(Modifier.fillMaxSize().background(BackroundMain).padding(12.dp))
    {
        upperText()
        Spacer(Modifier.padding(8.dp))
        MainDetail()
        Spacer(Modifier.padding(12.dp))
        TextRoute()
        Spacer(Modifier.padding(6.dp))
        MapBox()
    }

}

@Composable
@Preview
private fun MainDetail()
{
    Box(Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(Color.White))
    {
        Column(Modifier.padding(12.dp)) {
            Row(Modifier.fillMaxWidth()) {
                Icon(
                    painterResource(R.drawable.ic_launcher_foreground),
                    contentDescription = "",
                    Modifier.clip(RoundedCornerShape(12.dp)).size(36.dp)
                )
                Column {
                    Text("Аэрофлот")
                    Text("1ч 35м в полете")
                }
            }
            Row(Modifier.padding(top = 12.dp)) {
                TwoPointersItem()
                Column {
                    Row() {
                        Spacer(Modifier.padding(12.dp))
                        Column {
                            Text("16:55")
                            Text("25 окт, чт",color = TextTertiary)
                        }
                        Spacer(Modifier.padding(12.dp))
                        Column {
                            Text("Москва")
                            Text("Домодедово, DME",color = TextTertiary)
                        }

                    }
                    Row(Modifier.padding(top = 12.dp)) {
                        Spacer(Modifier.padding(12.dp))
                        Column {
                            Text("16:55")
                            Text("25 окт, чт",color = TextTertiary)
                        }
                        Spacer(Modifier.padding(12.dp))
                        Column {
                            Text("Москва")
                            Text("Домодедово, DME",color = TextTertiary)
                        }

                    }
                }
            }
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

@Composable
@Preview
private fun MapBox() {
    val context = LocalContext.current
    val mapView = remember { MapView(context) }

    DisposableEffect(Unit) {
        mapView.apply {
            setTileSource(TileSourceFactory.MAPNIK)
            controller.setZoom(9.5)
            zoomController.setVisibility(org.osmdroid.views.CustomZoomButtonsController.Visibility.NEVER)
            setMultiTouchControls(true)
            onResume()
        }
        onDispose {
            mapView.onPause()
            mapView.onDetach()
        }
    }

    Box(Modifier.wrapContentSize().clip(RoundedCornerShape(12.dp))
        .background(Color.White)) {
        AndroidView(
            factory = { mapView },
            modifier = Modifier.fillMaxWidth().height(300.dp).padding(12.dp)
        )
    }
}
@Composable
@Preview
private fun upperText()
{
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Москва", fontSize = 16.sp)
            HorizontalDivider(Modifier.height(1.dp).width(24.dp).background(Color.Black))
            Text("Санкт-Петербург", fontSize = 16.sp)
        }
        Row(Modifier.padding(top = 6.dp))
        {
            Text("1ч 35 в полете", color = TextTertiary)
        }
    }
}

@Composable
@Preview
private fun TextRoute()
{
    Row {
        Text("Маршрут подробнее", fontSize = 16.sp, fontStyle = FontStyle.Italic)
    }
}

@Composable
@Preview
private fun BuyTicketButton()
{
    Button(onClick = TODO(), colors = ButtonColors(
        containerColor = MainMenuBtn,
        contentColor = Color.White,
        disabledContentColor = Color.White,
        disabledContainerColor = MainMenuBtn
    )) { }
}

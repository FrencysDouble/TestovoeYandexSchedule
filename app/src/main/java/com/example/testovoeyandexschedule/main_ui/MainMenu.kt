package com.example.testovoeyandexschedule.main_ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AirplanemodeActive
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.twotone.AirplanemodeActive
import androidx.compose.material.icons.twotone.DirectionsBus
import androidx.compose.material.icons.twotone.Train
import androidx.compose.material.icons.twotone.Tram
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.testovoeyandexschedule.R
import com.example.testovoeyandexschedule.controllers.MainMenuController
import com.example.testovoeyandexschedule.controllers.SelectedText
import com.example.testovoeyandexschedule.controllers.TextStates
import com.example.testovoeyandexschedule.main_ui.navigation.Routes
import com.example.testovoeyandexschedule.models.SearchDataModel
import com.example.testovoeyandexschedule.ui.theme.GrayActive
import com.example.testovoeyandexschedule.ui.theme.GrayMain
import com.example.testovoeyandexschedule.ui.theme.IconBusColor
import com.example.testovoeyandexschedule.ui.theme.MainMenuBtn
import com.example.testovoeyandexschedule.ui.theme.TextFirstly
import com.example.testovoeyandexschedule.ui.theme.TextSecondary
import com.example.testovoeyandexschedule.ui.theme.TextTertiary
import java.time.LocalDate


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainMenu(
    navController: NavHostController,
    controller: MainMenuController,
    selectedText: SelectedText
)
{
    Scaffold(
        Modifier
            .fillMaxSize()) {
        Screen(navController,controller,selectedText)
    }
}


@SuppressLint("SuspiciousIndentation")
@Composable
private fun Screen(navController: NavHostController,controller: MainMenuController,selectedText: SelectedText)
{
    val searchData by controller.searchData.observeAsState(emptyList())
    val isLoadingLiveData by controller.isLoading.observeAsState(false)
    val isBadRequest by controller.isBadRequest.observeAsState(false)
    val isConnectionErrorMain by controller.isConnectionError.observeAsState(false)

        Column(
            Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(12.dp)) {
        Text(stringResource(id = R.string.main_menu_title), fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.padding(8.dp))
        UpperMenu(navController,controller,selectedText)
        Spacer(modifier = Modifier.padding(8.dp))
        DateChooserMenu(selectedText)
        Spacer(modifier = Modifier.padding(8.dp))
        TransportChooserMenu(selectedText)
        Spacer(modifier = Modifier.padding(8.dp))
        SearchButton(controller,selectedText)
        Spacer(modifier = Modifier.padding(12.dp))
        MainList(searchData,isLoadingLiveData,isBadRequest,isConnectionErrorMain)

    }

}

@Composable
fun UpperMenu(
    navController: NavHostController,
    controller: MainMenuController,
    selectedText: SelectedText
) {
    val liveDepartureData = selectedText.selectedDeparture.observeAsState()
    val liveDestinationData = selectedText.selectedDestination.observeAsState()
    Box(
        Modifier
            .wrapContentSize()
            .clip(RoundedCornerShape(6.dp))
            .background(Color.White)){

        Row(
            Modifier
                .fillMaxWidth()
                .border(2.dp, GrayMain, RoundedCornerShape(6.dp)), verticalAlignment = Alignment.CenterVertically) {

            Column(
                Modifier
                    .padding(12.dp)
                    .weight(1f)
            )
            {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(Routes.SearchScreen.route)
                            controller.setUpBarOption(TextStates.DEPARTURE)
                        }) {
                    liveDepartureData.value?.let { Text(text = it,color = GrayActive) }
                }
                Divider(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 8.dp))

                Column(
                    Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(Routes.SearchScreen.route)
                            controller.setUpBarOption(TextStates.DESTINATION)
                        }) {
                    liveDestinationData.value?.let { Text(text = it,color = GrayActive) }

                }
            }

            IconButton(onClick = { selectedText.shuffleText()},Modifier.wrapContentSize()) {
                Icon(
                    painter = painterResource(id = R.drawable.search_bar_shuffle_icon),
                    contentDescription = "",
                    Modifier.size(32.dp)
                )
            }
        }
    }
}


@Composable
fun DateChooserMenu(selectedText: SelectedText) {
    var selectedOption by remember { mutableStateOf(0) }
    var showDialog by remember { mutableStateOf(false) }

    Row(
        Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .background(if (selectedOption == 0) GrayActive else GrayMain)
                .fillMaxHeight()
                .weight(1f)
                .clickable {
                    selectedOption = 0
                    selectedText.setDate(
                        LocalDate
                            .now()
                            .toString()
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Сегодня", color = if (selectedOption == 0) Color.White else Color.Black)
        }
        Divider(
            Modifier
                .fillMaxHeight()
                .size(1.dp)
        )
        Box(
            modifier = Modifier
                .background(if (selectedOption == 1) GrayActive else GrayMain)
                .fillMaxHeight()
                .weight(1f)
                .clickable {
                    selectedOption = 1
                    selectedText.setDate(
                        LocalDate
                            .now()
                            .plusDays(1)
                            .toString()
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Завтра", color = if (selectedOption == 1) Color.White else Color.Black)
        }
        Divider(
            Modifier
                .fillMaxHeight()
                .size(1.dp)
        )
        Box(
            modifier = Modifier
                .background(if (selectedOption == 2) GrayActive else GrayMain)
                .fillMaxHeight()
                .weight(1f)
                .clickable {
                    selectedOption = 2
                    showDialog = true
                },
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Дата", color = if (selectedOption == 2) Color.White else Color.Black)
        }
    }

    if (showDialog) {
        CalendarDialog(
            onDismissRequest = { showDialog = false },
            onDateSelected = { date ->
                selectedText.setDate(date.toString())
                showDialog = false
            }
        )
    }
}

@Composable
fun TransportChooserMenu(selectedText: SelectedText) {
    var selectedOption by remember { mutableStateOf(0) }

    Row(
        Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color.White)) {

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .clip(RoundedCornerShape(6.dp))
                .weight(1f)
                .background(if (selectedOption == 0) GrayActive else GrayMain)
                .clickable {
                    selectedOption = 0
                    selectedText.setTransportType("all")
                },
            contentAlignment = Alignment.Center
        ) {
            Text(text = "любой",color = if (selectedOption == 0) Color.White else Color.Black)
        }

        Spacer(modifier = Modifier.width(8.dp))

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .clip(RoundedCornerShape(6.dp))
                .background(if (selectedOption == 1) GrayActive else GrayMain)
                .weight(1f)
                .clickable {
                    selectedOption = 1
                    selectedText.setTransportType("plane")
                    Log.d(
                        "Transport",
                        selectedText.selectedDate.value?.transportType ?: "no transp"
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = Icons.TwoTone.AirplanemodeActive, contentDescription = "", Modifier.size(24.dp))
        }

        Spacer(modifier = Modifier.width(8.dp))

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .clip(RoundedCornerShape(6.dp))
                .background(if (selectedOption == 2) GrayActive else GrayMain)
                .weight(1f)
                .clickable {
                    selectedOption = 2
                    selectedText.setTransportType("train")
                    Log.d(
                        "Transport",
                        selectedText.selectedDate.value?.transportType ?: "no transp"
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = Icons.TwoTone.Train, contentDescription = "", Modifier.size(24.dp))
        }

        Spacer(modifier = Modifier.width(8.dp))

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .clip(RoundedCornerShape(6.dp))
                .background(if (selectedOption == 3) GrayActive else GrayMain)
                .weight(1f)
                .clickable {
                    selectedOption = 3
                    selectedText.setTransportType("suburban")
                    Log.d(
                        "Transport",
                        selectedText.selectedDate.value?.transportType ?: "no transp"
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = Icons.TwoTone.Tram, contentDescription = "", Modifier.size(24.dp))
        }

        Spacer(modifier = Modifier.width(8.dp))

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .clip(RoundedCornerShape(6.dp))
                .background(if (selectedOption == 4) GrayActive else GrayMain)
                .weight(1f)
                .clickable {
                    selectedOption = 4
                    selectedText.setTransportType("bus")
                    Log.d(
                        "Transport",
                        selectedText.selectedDate.value?.transportType ?: "no transp"
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = Icons.TwoTone.DirectionsBus, contentDescription = "", Modifier.size(24.dp))
        }
    }
}
@Composable
fun SearchButton(controller: MainMenuController, selectedText: SelectedText)
{

    Button(onClick = {
        controller.searchDataLoad()},Modifier.fillMaxWidth(),
        shape = ButtonDefaults.elevatedShape,
        colors = ButtonDefaults.buttonColors(
            contentColor = TextFirstly,
            containerColor = MainMenuBtn
        ) ) {

        Text(text = "Найти")
        
    }
}

@Composable
private fun MainList(
    searchData: List<SearchDataModel>,
    isLoading : Boolean,
    isBadRequest : Boolean,
    isConnection : Boolean)
{
    LazyColumn()
    {
        if (isLoading) {
            item {
                ErrorsScreens.LoadingBadge()
            }
        }
        if (isBadRequest) {
            item {
                ErrorsScreens.BadRequest()
            }
        }
        if (isConnection) {
            item {
                ErrorsScreens.ConnectionError()
            }
        }
        items(searchData) { item ->

            MainListItem(item)
        }
    }

}


@Composable
private fun MainListItem(item: SearchDataModel)
{
    Column(Modifier.background(Color.White)) {
        Row(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color.White)
        ) {

            Icon(
                imageVector = item.vectorIcon,
                contentDescription = "",
                tint = IconBusColor
            )
            Column(
                Modifier
                    .wrapContentSize()
                    .width(100.dp)
            ) {
                Text(text = item.fullRouteTitle)
                Spacer(modifier = Modifier.padding(4.dp))
                Text(text = item.density, color = TextTertiary)
                item.vehicleName?.let { Text(text = it, color = TextSecondary) }


            }
            Row(Modifier.padding(start = 12.dp)) {
                Column(Modifier.weight(1f)) {
                    Text(text = item.departureDate, color = TextSecondary)
                    Text(text = item.departureTime, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.padding(2.dp))
                    Text(text = item.titleFrom, color = TextTertiary)
                    //Text(text = "Терминал А",color = TextTertiary)
                }

                Column(
                    Modifier
                        .height(50.dp)
                        .padding(start = 6.dp, end = 12.dp, top = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = item.duration, color = TextSecondary)
                }

                Column(Modifier.weight(1f)) {
                    Text(text = item.arrivalDate, color = TextSecondary)
                    Text(text = item.arrivalTime, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.padding(2.dp))
                    Text(text = item.titleTo, color = TextTertiary)
                    //Text(text = "Терминал А",color = TextTertiary)
                }
            }
        }

        Column (Modifier.fillMaxWidth().padding(start = 24.dp, top = 8.dp))
        {
            Text("Отбытие через: ${item.timeLeft}",
                fontSize = 14.sp,
                fontStyle = FontStyle.Italic)
        }
        Spacer(modifier = Modifier.padding(12.dp))

    }
}

@Preview
@Composable
private fun preview()
{
    val item : SearchDataModel = SearchDataModel(
        "12:00",
        "13 окт",
        "13:00",
        "12 окт",
        "6 ч 05 мин",
        "Москва -- Уфа",
        "Boeing 777",
        "Уфа",
        "Москва",
                "",
        Icons.Filled.AirplanemodeActive,
        "12:00")
    MainListItem(item)
}


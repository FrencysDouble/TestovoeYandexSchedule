package com.example.testovoeyandexschedule.main_ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.testovoeyandexschedule.R
import com.example.testovoeyandexschedule.controllers.MainMenuController
import com.example.testovoeyandexschedule.controllers.SelectedText
import com.example.testovoeyandexschedule.controllers.TextStates
import com.example.testovoeyandexschedule.main_ui.navigation.Routes
import com.example.testovoeyandexschedule.models.StationsModel
import com.example.testovoeyandexschedule.ui.theme.GrayMain
import com.example.testovoeyandexschedule.ui.theme.TextSecondary


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchMenu(
    navController: NavHostController,
    controller: MainMenuController,
    selectedText: SelectedText
)
{
    Scaffold(Modifier.fillMaxSize()) {
        Screen(navController,controller,selectedText)
    }

    LaunchedEffect(Unit)
    {
        controller.stationsDataLoad()
    }
}


@Composable
private fun Screen(navController: NavHostController, controller: MainMenuController,selectedText: SelectedText)
{
    val liveTextTitle = controller.upBarChooseOption.observeAsState()
    val filteredData by controller.filteredStationsData.observeAsState(emptyList())
    val searchText by controller.searchText.observeAsState("")
    val isLoadingLiveDataSearch by controller.isLoading.observeAsState(false)
    val isConnectionErrorSearch by controller.isConnectionError.observeAsState(false)
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(12.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.navigate(Routes.MainScreen.route)}) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription ="" )
                
            }
            liveTextTitle.value?.let { Text(text = controller.getUpBarOptions(), fontSize = 20.sp) }
        }

        SearchTextField(
            searchText = searchText,
            onTextChange = { text ->
                controller.updateSearchText(text)
            },
            controller.getUpBarOptions()

        )
        
        Spacer(modifier = Modifier.padding(12.dp))

        MainList(
            filteredData,
            liveTextTitle,
            navController,
            selectedText,
            isLoadingLiveDataSearch,
            isConnectionErrorSearch
        )

    }
}

@Composable
fun SearchTextField(
    searchText: String,
    onTextChange: (String) -> Unit,
    upBarOptions: String
) {
    val focusManager = LocalFocusManager.current

    BasicTextField(
        value = searchText,
        onValueChange = onTextChange,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .border(1.dp, GrayMain, RoundedCornerShape(6.dp))
            .padding(vertical = 8.dp, horizontal = 2.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
            },
        ),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(12.dp)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    if (searchText.isEmpty()) {
                        Text(
                            text = upBarOptions,
                            color = Color.Gray,
                            fontSize = 16.sp,
                            modifier = Modifier.align(Alignment.CenterStart)
                        )
                    }
                    innerTextField()
                }
                IconButton(onClick = { onTextChange("") },
                    Modifier
                        .width(24.dp)
                        .height(24.dp)) {
                    Icon(
                        painter = painterResource(id = R.drawable.dialog_icon_delete),
                        contentDescription = "Clear text",
                        tint = Color.Gray
                    )
                }
            }
        },
        textStyle = TextStyle(color = Color.Black, fontSize = 16.sp)
    )
}
@Composable
private fun MainList(
    data: List<StationsModel>,
    liveTextTitle: State<TextStates?>,
    navController: NavHostController,
    selectedText: SelectedText,
    isLoading : Boolean,
    isConnectionError : Boolean
)
{
    LazyColumn()
    {
        if (isLoading) {
            item {
                ErrorsScreens.LoadingBadge()
            }
        }
        if (isConnectionError) {
            item {
                ErrorsScreens.ConnectionError()
            }
        }
        items(data) { item ->
            MainListItem(item,liveTextTitle,navController,selectedText)
        }
    }

}


@Composable
private fun MainListItem(
    item : StationsModel,
    liveTextTitle: State<TextStates?>,
    navController: NavHostController,
    selectedText: SelectedText)
{
    Column(
        Modifier
            .fillMaxWidth()
            .clickable {
                when (liveTextTitle.value) {
                    TextStates.DEPARTURE -> {
                        selectedText.setDeparture(item.stationsTitle)
                        item.settlementsCode?.let { selectedText.setIdFromStation(it) }
                    }

                    TextStates.DESTINATION -> {
                        selectedText.setDestination(item.stationsTitle)
                        item.settlementsCode?.let { selectedText.setIdToStation(it) }
                    }

                    else -> {}
                }
                navController.navigate(Routes.MainScreen.route)
            }) {
        Text(item.stationsTitle)
        Text(item.settlementsTitle, color = TextSecondary, fontSize = 14.sp)
    }
        Divider(
            Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, bottom = 12.dp))


}

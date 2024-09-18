package com.example.testovoeyandexschedule.network

import com.example.testovoeyandexschedule.controllers.SelectedText
import com.example.testovoeyandexschedule.di.ApiModule
import com.example.testovoeyandexschedule.di.ControllersModule
import com.example.testovoeyandexschedule.models.SearchDataModel
import com.example.testovoeyandexschedule.models.StationsModel
import com.example.testovoeyandexschedule.network.api.ApiResponse
import com.example.testovoeyandexschedule.network.api.MainMenuApi
import com.example.testovoeyandexschedule.network.api.handleApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MainApiController(private val apiModule: ApiModule): MainMenuApiInterface {

    private val mainMenuApi: MainMenuApi
        get() = apiModule.provideMainMenuApi()


    override suspend fun getStationsData(): Flow<ApiResponse<List<StationsModel>>> =
        flow {
            emit(ApiResponse.Loading)
            val response = handleApiResponse(
                call = { mainMenuApi.getStationsList() },
                map = { StationsModel.map(it) }
            )
            emit(response)
        }

    override suspend fun getSearchData(selectedText: SelectedText): Flow<ApiResponse<List<SearchDataModel>>> =
        flow {
            emit(ApiResponse.Loading)
            val response = handleApiResponse(
                call = {mainMenuApi.getSearchRouteData(selectedText.selectedDate.value!!)},
                map = {SearchDataModel.map(it)}
            )
            emit(response)
        }

}


interface MainMenuApiInterface
{

    suspend fun getStationsData() : Flow<ApiResponse<List<StationsModel>>>

    suspend fun getSearchData(selectedText: SelectedText) : Flow<ApiResponse<List<SearchDataModel>>>
}
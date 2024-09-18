package com.example.testovoeyandexschedule.network.api

import com.example.testovoeyandexschedule.controllers.SelectedText
import com.example.testovoeyandexschedule.controllers.UISelectedData
import com.example.testovoeyandexschedule.models.dto.SearchDataDTO
import com.example.testovoeyandexschedule.models.dto.StationsModelDTO
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

class MainMenuApi(private val retrofit: Retrofit) {
    private val service : MainMenuApiPoints = retrofit.create(MainMenuApiPoints::class.java)


    suspend fun getStationsList() : Response<StationsModelDTO> = service.getStationsList()

    suspend fun getSearchRouteData(queryData: UISelectedData): Response<SearchDataDTO> = service.getSearchRouteData(
        queryData.idFromStation,
        queryData.idToStation,
        queryData.date,
        queryData.transportType
    )
}


interface MainMenuApiPoints
{
    @GET(ApiRoutes.STATIONS_LIST)
    suspend fun getStationsList() : Response<StationsModelDTO>

    @GET(ApiRoutes.SEARCH_ROUTE)
    suspend fun getSearchRouteData(
        @Query("from") from : String,
        @Query("to") to : String,
        @Query("date") date : String,
        @Query("transport_types") transportType : String?
    ) : Response<SearchDataDTO>
}
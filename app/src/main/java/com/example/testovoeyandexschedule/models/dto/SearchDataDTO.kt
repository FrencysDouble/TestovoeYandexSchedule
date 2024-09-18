package com.example.testovoeyandexschedule.models.dto

import java.time.OffsetDateTime


data class SearchDataDTO(
    val segments : List<Segments>
)

data class Segments(
    val thread : Thread,
    val from : From,
    val to : To,
    val arrival : OffsetDateTime,
    val departure : OffsetDateTime,
    val duration : Int
)


data class Thread(
    val title : String,
    val vehicle : String?,
    val interval : Interval?
)

data class From(
    val title : String,
    val transport_type : String
)

data class To(
    val title : String,
    val transport_type : String
)

data class Interval(
    val density: String
)
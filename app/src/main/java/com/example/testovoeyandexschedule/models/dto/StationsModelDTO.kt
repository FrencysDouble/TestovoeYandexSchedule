package com.example.testovoeyandexschedule.models.dto

data class StationsModelDTO(
    val countries : List<Countries>
)

data class Countries(
    val regions : List<Regions>,
    val title : String,
)

data class Regions(
    val settlements : List<Settlements>,
    val title : String,
)

data class Settlements(
    val title: String,
    val stations : List<Stations>,
    val codes : Codes?
)

data class Stations(
    val title : String,
    val codes: Codes
)

data class Codes(
    val yandex_code : String?
)

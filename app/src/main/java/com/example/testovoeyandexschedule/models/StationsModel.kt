package com.example.testovoeyandexschedule.models

import com.example.testovoeyandexschedule.models.dto.StationsModelDTO

data class StationsModel(
    val regionTitle: String,
    val settlementsTitle: String,
    val stationsTitle: String,
    val stationCode : String?,
    val settlementsCode : String?

) {
    companion object {
        fun map(dto: StationsModelDTO): List<StationsModel> {
            val mappedData = mutableListOf<StationsModel>()

            dto.countries.forEach { country ->
                country.regions.forEach { region ->
                    region.settlements.forEach { settlement ->
                        settlement.stations.forEach { station ->
                            mappedData.add(
                                StationsModel(
                                    regionTitle = region.title,
                                    settlementsTitle = settlement.title,
                                    stationsTitle = station.title,
                                    stationCode = station.codes.yandex_code,
                                    settlementsCode = settlement.codes?.yandex_code
                                )
                            )
                        }
                    }
                }
            }

            return mappedData
        }
    }
}
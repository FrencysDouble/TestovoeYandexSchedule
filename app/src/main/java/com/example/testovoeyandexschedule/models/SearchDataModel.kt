package com.example.testovoeyandexschedule.models

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Airlines
import androidx.compose.material.icons.filled.AirplanemodeActive
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Train
import androidx.compose.material.icons.filled.Tram
import androidx.compose.material.icons.filled.Water
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.testovoeyandexschedule.models.dto.SearchDataDTO
import java.time.Duration
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

data class SearchDataModel(
    val arrivalTime: String,
    val arrivalDate: String,
    val departureTime: String,
    val departureDate: String,
    val duration: String,
    val fullRouteTitle: String,
    val vehicleName: String?,
    val titleFrom: String,
    val titleTo: String,
    val density: String,
    val vectorIcon : ImageVector,
    val timeLeft : String
) {
    companion object {
        private val dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

        @SuppressLint("DefaultLocale")
        @RequiresApi(Build.VERSION_CODES.S)
        fun map(dto: SearchDataDTO): List<SearchDataModel> {
            return dto.segments.map { segment ->
                val arrivalDateTime = OffsetDateTime.parse(segment.arrival.toString(), dateTimeFormatter)
                val departureDateTime = OffsetDateTime.parse(segment.departure.toString(), dateTimeFormatter)

                val arrivalDateFormatted = arrivalDateTime.format(DateTimeFormatter.ofPattern("d MMM", Locale("ru")))
                val departureDateFormatted = departureDateTime.format(DateTimeFormatter.ofPattern("d MMM", Locale("ru")))

                val hours = segment.duration / 3600
                val minutes = (segment.duration % 3600) / 60
                val durationFormatted = String.format("%d ч %02d мин", hours, minutes)
                val now = OffsetDateTime.now()
                val timeLeft = if (departureDateTime.isAfter(now)) {
                    Duration.between(now, departureDateTime)
                } else {
                    Duration.ZERO
                }
                val timeLeftFormatted = String.format(
                    "%d ч %02d мин",
                    timeLeft.toHours(),
                    timeLeft.toMinutesPart()
                )

                val vector = when(segment.to.transport_type)
                {
                    "plane" -> Icons.Filled.AirplanemodeActive
                    "train" -> Icons.Filled.Train
                    "suburban" -> Icons.Filled.Tram
                    "bus" -> Icons.Filled.DirectionsBus
                    "water" -> Icons.Filled.Water
                    "helicopter" -> Icons.Filled.Airlines
                    else -> {Icons.Filled.AddAPhoto}
                }

                SearchDataModel(
                    arrivalTime = arrivalDateTime.toLocalTime().toString(),
                    arrivalDate = arrivalDateFormatted,
                    departureTime = departureDateTime.toLocalTime().toString(),
                    departureDate = departureDateFormatted,
                    duration = durationFormatted,
                    fullRouteTitle = segment.thread.title,
                    vehicleName = segment.thread.vehicle,
                    titleFrom = segment.from.title,
                    titleTo = segment.to.title,
                    density = segment.thread.interval?.density ?: "",
                    vectorIcon = vector,
                    timeLeft = timeLeftFormatted
                )
            }
        }
    }
}
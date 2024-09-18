package com.example.testovoeyandexschedule.network.utils

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class OffsetDateTimeAdapter {

    private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    @FromJson
    fun fromJson(dateString: String): OffsetDateTime {
        return OffsetDateTime.parse(dateString, formatter)
    }

    @ToJson
    fun toJson(date: OffsetDateTime): String {
        return date.format(formatter)
    }
}
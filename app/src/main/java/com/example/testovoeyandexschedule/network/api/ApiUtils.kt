package com.example.testovoeyandexschedule.network.api

import retrofit2.Response

suspend fun <T, R> handleApiResponse(
    call: suspend () -> Response<T>,
    map: (T) -> R
): ApiResponse<R> {
    return try {
        val response = call()
        if (response.isSuccessful) {
            response.body()?.let {
                ApiResponse.Success(map(it))
            } ?: ApiResponse.BadRequest("Empty body")
        } else {
            when (response.code()) {
                400 -> ApiResponse.BadRequest("Invalid request parameters")
                else -> ApiResponse.BadRequest("API call failed with error: ${response.message()}")
            }
        }
    } catch (e: java.io.IOException) {
        ApiResponse.NetworkError
    } catch (e: Exception) {
        ApiResponse.BadRequest("Exception occurred: ${e.message}")
    }
}

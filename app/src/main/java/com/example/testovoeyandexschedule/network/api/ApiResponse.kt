package com.example.testovoeyandexschedule.network.api

sealed class ApiResponse<out T> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    object Loading : ApiResponse<Nothing>()
    data class BadRequest(val message: String) : ApiResponse<Nothing>()
    object NetworkError : ApiResponse<Nothing>()
}
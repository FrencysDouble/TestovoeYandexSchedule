package com.example.testovoeyandexschedule.controllers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.sql.Time
import java.time.LocalDate

class SelectedText() : ViewModel() {


    private val _selectedDeparture = MutableLiveData("Москва")
    val selectedDeparture: LiveData<String> = _selectedDeparture

    private val _selectedDestination = MutableLiveData("Куда")
    val selectedDestination: LiveData<String> = _selectedDestination


    private val _selectedData = MutableLiveData(UISelectedData("", "c213", LocalDate.now().toString(), "",))
    val selectedDate: LiveData<UISelectedData> = _selectedData


    fun setDeparture(text : String)
    {
        _selectedDeparture.value = text
    }

    fun setDestination(text: String)
    {
        _selectedDestination.value = text
    }

    fun setIdToStation(id: String) {
        _selectedData.value = _selectedData.value?.copy(idToStation = id)
    }

    fun setIdFromStation(id: String) {
        _selectedData.value = _selectedData.value?.copy(idFromStation = id)
    }

    fun setTransportType(type: String) {
        _selectedData.value = _selectedData.value?.copy(transportType = type)
    }

    fun setDate(date: String) {
        _selectedData.value = _selectedData.value?.copy(date = date)
    }

    fun shuffleText()
    {
        val dep = _selectedDeparture.value
        val dest = _selectedDestination.value
        val setIdFrom = _selectedData.value?.idFromStation
        val setIdTo = _selectedData.value?.idToStation


        _selectedDeparture.value = dest
        _selectedDestination.value = dep

        _selectedData.value = setIdTo?.let {
            setIdFrom?.let { it1 ->
                _selectedData.value?.copy(
                    idFromStation = it,
                    idToStation = it1
                )
            }
        }
    }

}

enum class TextStates
{
    DEPARTURE,
    DESTINATION
}

data class UISelectedData(
    var idToStation : String,
    var idFromStation : String,
    var date : String,
    var transportType : String,
)
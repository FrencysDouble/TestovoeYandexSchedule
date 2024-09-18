package com.example.testovoeyandexschedule.controllers

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testovoeyandexschedule.models.SearchDataModel
import com.example.testovoeyandexschedule.models.StationsModel
import com.example.testovoeyandexschedule.network.MainMenuApiInterface
import com.example.testovoeyandexschedule.network.api.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainMenuController(
    private val mainMenuApiInterface: MainMenuApiInterface,
    private val selectedText: SelectedText
) : ViewModel() {


    private val _allStationsData = MutableLiveData<List<StationsModel>>(emptyList())
    val allStationsData: LiveData<List<StationsModel>> get() = _allStationsData

    private val _filteredStationsData = MutableLiveData<List<StationsModel>>(emptyList())
    val filteredStationsData: LiveData<List<StationsModel>> get() = _filteredStationsData

    private val _searchData = MutableLiveData<List<SearchDataModel>>(emptyList())
    val searchData : LiveData<List<SearchDataModel>> get() = _searchData

    private val _searchText = MutableLiveData("")
    val searchText: LiveData<String> get() = _searchText

    val isLoading : MutableLiveData<Boolean> = MutableLiveData(false)

    val isBadRequest : MutableLiveData<Boolean> = MutableLiveData(false)

    val isConnectionError : MutableLiveData<Boolean> = MutableLiveData(false)

    var upBarChooseOption : MutableLiveData<TextStates> = MutableLiveData()

    fun setUpBarOption(state: TextStates) {
        upBarChooseOption.value = state
    }

    fun getUpBarOptions(): String {
        return when (upBarChooseOption.value) {
            TextStates.DEPARTURE -> "Откуда"
            TextStates.DESTINATION -> "Куда"
            else -> {""}
        }
    }


    fun stationsDataLoad()
    {
        viewModelScope.launch {
            mainMenuApiInterface.getStationsData().collect{response ->
                withContext(Dispatchers.Main)
                {
                    when(response)
                    {
                        is ApiResponse.Success -> {

                            _allStationsData.value = response.data
                            filterData(_searchText.value.orEmpty())
                            Log.d("Stations", "Success: ${response.data}")
                            isLoading.value = false
                        }
                        is ApiResponse.Loading -> {
                            Log.d("Stations","Loading")
                            _filteredStationsData.value = emptyList()
                            isLoading.value = true
                        }
                        is ApiResponse.BadRequest ->{
                            Log.d("Stations",response.message)
                            isLoading.value = false
                        }
                        is ApiResponse.NetworkError -> {
                            Log.d("Stations","network_error")
                            isLoading.value = false
                            isConnectionError.value = true
                        }
                    }
                }
            }
        }
    }

    fun searchDataLoad()
    {

        viewModelScope.launch {
            mainMenuApiInterface.getSearchData(selectedText).collect{response ->
                withContext(Dispatchers.Main)
                {
                    when(response)
                    {
                        is ApiResponse.Success -> {
                            _searchData.value = response.data
                            Log.d("Search", "Success: ${response.data}")
                            isLoading.value = false
                            isBadRequest.value = false
                            isConnectionError.value = false
                        }
                        is ApiResponse.Loading -> {
                            Log.d("Search_Loading","Loading")
                            _searchData.value = emptyList()
                            isLoading.value = true
                            isBadRequest.value = false
                            isConnectionError.value = false
                        }
                        is ApiResponse.BadRequest ->{
                            Log.d("Search_Bad_Request",response.message)
                            isLoading.value = false
                            isBadRequest.value = true
                            isConnectionError.value = false
                        }
                        is ApiResponse.NetworkError -> {
                            Log.d("Search","network_error")
                            isLoading.value = false
                            isConnectionError.value = true
                            isBadRequest.value = false
                        }
                    }
                }
            }
        }
    }

    fun updateSearchText(text: String) {
        _searchText.value = text
        filterData(text)
    }

    fun filterData(searchText: String) {
        val filtered = _allStationsData.value?.filter {
                    it.settlementsTitle.contains(searchText, ignoreCase = true) ||
                    it.stationsTitle.contains(searchText,ignoreCase = true)
        }?.take(8) ?: emptyList()
        _filteredStationsData.value = filtered
    }
}
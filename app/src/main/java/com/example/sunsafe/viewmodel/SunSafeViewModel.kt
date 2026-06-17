package com.example.sunsafe.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sunsafe.data.WeatherRepository
import com.example.sunsafe.data.model.LocationPreset
import com.example.sunsafe.data.model.TemperatureUnit
import com.example.sunsafe.data.model.UvSensitivity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SunSafeViewModel(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SunSafeUiState())
    val uiState: StateFlow<SunSafeUiState> = _uiState.asStateFlow()

    init {
        refreshWeather()
    }

    fun refreshWeather() {
        val currentState = _uiState.value

        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(isLoading = true, errorMessage = null)
            }

            val result = repository.fetchWeatherSummary(
                location = currentState.selectedLocation,
                temperatureUnit = currentState.temperatureUnit,
                uvSensitivity = currentState.uvSensitivity
            )

            result.onSuccess { summary ->
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        weatherSummary = summary,
                        errorMessage = null
                    )
                }
            }.onFailure { error ->
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        errorMessage = error.message ?: "Unable to load weather data."
                    )
                }
            }
        }
    }

    fun selectLocation(location: LocationPreset) {
        _uiState.update { state -> state.copy(selectedLocation = location) }
        refreshWeather()
    }

    fun selectTemperatureUnit(unit: TemperatureUnit) {
        _uiState.update { state -> state.copy(temperatureUnit = unit) }
        refreshWeather()
    }

    fun selectUvSensitivity(sensitivity: UvSensitivity) {
        _uiState.update { state -> state.copy(uvSensitivity = sensitivity) }
        refreshWeather()
    }

    fun setShowHourlyUv(showHourlyUv: Boolean) {
        _uiState.update { state -> state.copy(showHourlyUv = showHourlyUv) }
    }
}

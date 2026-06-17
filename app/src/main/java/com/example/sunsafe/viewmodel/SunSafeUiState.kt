package com.example.sunsafe.viewmodel

import com.example.sunsafe.data.model.LocationPreset
import com.example.sunsafe.data.model.TemperatureUnit
import com.example.sunsafe.data.model.UvSensitivity
import com.example.sunsafe.data.model.WeatherSummary

data class SunSafeUiState(
    val selectedLocation: LocationPreset = LocationPreset.Singapore,
    val temperatureUnit: TemperatureUnit = TemperatureUnit.Celsius,
    val uvSensitivity: UvSensitivity = UvSensitivity.Normal,
    val showHourlyUv: Boolean = true,
    val isLoading: Boolean = false,
    val weatherSummary: WeatherSummary? = null,
    val errorMessage: String? = null
)

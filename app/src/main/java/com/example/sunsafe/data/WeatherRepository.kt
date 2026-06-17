package com.example.sunsafe.data

import com.example.sunsafe.data.model.LocationPreset
import com.example.sunsafe.data.model.TemperatureUnit
import com.example.sunsafe.data.model.UvSensitivity
import com.example.sunsafe.data.model.WeatherSummary

interface WeatherRepository {
    suspend fun fetchWeatherSummary(
        location: LocationPreset,
        temperatureUnit: TemperatureUnit,
        uvSensitivity: UvSensitivity
    ): Result<WeatherSummary>
}

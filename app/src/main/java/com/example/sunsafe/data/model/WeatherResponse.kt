package com.example.sunsafe.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    @SerialName("latitude") val latitude: Double? = null,
    @SerialName("longitude") val longitude: Double? = null,
    @SerialName("timezone") val timezone: String? = null,
    @SerialName("current") val current: CurrentWeather? = null,
    @SerialName("current_units") val currentUnits: CurrentUnits? = null,
    @SerialName("hourly") val hourly: HourlyWeather? = null,
    @SerialName("hourly_units") val hourlyUnits: HourlyUnits? = null
)

@Serializable
data class CurrentWeather(
    @SerialName("time") val time: String? = null,
    @SerialName("temperature_2m") val temperature: Double? = null,
    @SerialName("apparent_temperature") val apparentTemperature: Double? = null,
    @SerialName("weather_code") val weatherCode: Int? = null,
    @SerialName("is_day") val isDay: Int? = null
)

@Serializable
data class CurrentUnits(
    @SerialName("temperature_2m") val temperatureUnit: String? = null,
    @SerialName("apparent_temperature") val apparentTemperatureUnit: String? = null
)

@Serializable
data class HourlyWeather(
    @SerialName("time") val time: List<String> = emptyList(),
    @SerialName("temperature_2m") val temperatures: List<Double> = emptyList(),
    @SerialName("uv_index") val uvIndex: List<Double> = emptyList()
)

@Serializable
data class HourlyUnits(
    @SerialName("temperature_2m") val temperatureUnit: String? = null,
    @SerialName("uv_index") val uvIndexUnit: String? = null
)

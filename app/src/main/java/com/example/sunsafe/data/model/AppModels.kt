package com.example.sunsafe.data.model

import kotlin.math.roundToInt

enum class TemperatureUnit(val displayName: String, val apiValue: String, val symbol: String) {
    Celsius("Celsius", "celsius", "°C"),
    Fahrenheit("Fahrenheit", "fahrenheit", "°F")
}

enum class UvSensitivity(val displayName: String) {
    Normal("Normal"),
    Sensitive("Sensitive")
}

enum class LocationPreset(
    val displayName: String,
    val latitude: Double,
    val longitude: Double
) {
    Singapore("Singapore", 1.3521, 103.8198),
    Townsville("Townsville", -19.2590, 146.8169),
    Tokyo("Tokyo", 35.6762, 139.6503)
}

data class HourlyUv(
    val timeLabel: String,
    val uvIndex: Double
)

data class WeatherSummary(
    val location: LocationPreset,
    val temperature: Double,
    val apparentTemperature: Double?,
    val temperatureUnit: TemperatureUnit,
    val uvIndex: Double,
    val uvRisk: UvRisk,
    val advice: String,
    val conditionText: String,
    val hourlyUv: List<HourlyUv>
) {
    val roundedTemperature: String
        get() = temperature.roundToInt().toString()

    val roundedFeelsLike: String
        get() = apparentTemperature?.roundToInt()?.toString() ?: "--"

    val formattedUv: String
        get() = String.format("%.1f", uvIndex)
}

enum class UvRisk(val label: String, val message: String) {
    Low("Low", "Safe for normal outdoor activity."),
    Moderate("Moderate", "Use sunscreen if you will stay outside."),
    High("High", "Wear sunscreen, sunglasses, and seek shade."),
    VeryHigh("Very High", "Avoid long outdoor activity during peak hours."),
    Extreme("Extreme", "Stay in shade as much as possible.")
}

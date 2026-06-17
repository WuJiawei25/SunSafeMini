package com.example.sunsafe.data

import com.example.sunsafe.data.model.HourlyUv
import com.example.sunsafe.data.model.LocationPreset
import com.example.sunsafe.data.model.TemperatureUnit
import com.example.sunsafe.data.model.UvRisk
import com.example.sunsafe.data.model.UvSensitivity
import com.example.sunsafe.data.model.WeatherResponse
import com.example.sunsafe.data.model.WeatherSummary

class WeatherRepositoryImpl(
    private val api: WeatherApi
) : WeatherRepository {

    override suspend fun fetchWeatherSummary(
        location: LocationPreset,
        temperatureUnit: TemperatureUnit,
        uvSensitivity: UvSensitivity
    ): Result<WeatherSummary> {
        return runCatching {
            val response = api.getForecast(
                latitude = location.latitude,
                longitude = location.longitude,
                temperatureUnit = temperatureUnit.apiValue
            )
            response.toWeatherSummary(location, temperatureUnit, uvSensitivity)
        }
    }

    private fun WeatherResponse.toWeatherSummary(
        location: LocationPreset,
        temperatureUnit: TemperatureUnit,
        uvSensitivity: UvSensitivity
    ): WeatherSummary {
        val hourlyData = hourly
        val currentTime = current?.time
        val currentIndex = findCurrentIndex(currentTime, hourlyData?.time.orEmpty())

        val temperature = current?.temperature
            ?: hourlyData?.temperatures?.getOrNull(currentIndex)
            ?: 0.0
        val uvIndex = hourlyData?.uvIndex?.getOrNull(currentIndex) ?: 0.0
        val nextHourlyUv = buildNextHourlyUv(hourlyData?.time.orEmpty(), hourlyData?.uvIndex.orEmpty(), currentIndex)
        val risk = calculateUvRisk(uvIndex, uvSensitivity)

        return WeatherSummary(
            location = location,
            temperature = temperature,
            apparentTemperature = current?.apparentTemperature,
            temperatureUnit = temperatureUnit,
            uvIndex = uvIndex,
            uvRisk = risk,
            advice = risk.message,
            conditionText = weatherCodeToText(current?.weatherCode),
            hourlyUv = nextHourlyUv
        )
    }

    private fun findCurrentIndex(currentTime: String?, hourlyTimes: List<String>): Int {
        if (hourlyTimes.isEmpty()) return 0
        if (currentTime == null) return 0

        val exactIndex = hourlyTimes.indexOf(currentTime)
        if (exactIndex >= 0) return exactIndex

        val currentHour = currentTime.take(13)
        val approximateIndex = hourlyTimes.indexOfFirst { time -> time.take(13) == currentHour }
        return if (approximateIndex >= 0) approximateIndex else 0
    }

    private fun buildNextHourlyUv(
        times: List<String>,
        uvValues: List<Double>,
        startIndex: Int
    ): List<HourlyUv> {
        val result = mutableListOf<HourlyUv>()
        val endIndex = minOf(startIndex + 6, times.lastIndex)

        for (index in startIndex..endIndex) {
            val label = times.getOrNull(index)?.substringAfter("T") ?: "--:--"
            val uv = uvValues.getOrNull(index) ?: 0.0
            result.add(HourlyUv(label, uv))
        }

        return result
    }

    private fun calculateUvRisk(uvIndex: Double, sensitivity: UvSensitivity): UvRisk {
        val adjustedUv = if (sensitivity == UvSensitivity.Sensitive) uvIndex + 2.0 else uvIndex

        return when {
            adjustedUv < 3.0 -> UvRisk.Low
            adjustedUv < 6.0 -> UvRisk.Moderate
            adjustedUv < 8.0 -> UvRisk.High
            adjustedUv < 11.0 -> UvRisk.VeryHigh
            else -> UvRisk.Extreme
        }
    }

    private fun weatherCodeToText(code: Int?): String {
        return when (code) {
            0 -> "Clear sky"
            1, 2, 3 -> "Partly cloudy"
            45, 48 -> "Foggy"
            51, 53, 55 -> "Light drizzle"
            61, 63, 65 -> "Rainy"
            71, 73, 75 -> "Snowy"
            80, 81, 82 -> "Rain showers"
            95, 96, 99 -> "Thunderstorm"
            else -> "Outdoor conditions"
        }
    }
}

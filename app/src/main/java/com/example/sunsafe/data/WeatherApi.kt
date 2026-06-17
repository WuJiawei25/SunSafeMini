package com.example.sunsafe.data

import com.example.sunsafe.data.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("v1/forecast")
    suspend fun getForecast(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("current") current: String = "temperature_2m,apparent_temperature,weather_code,is_day",
        @Query("hourly") hourly: String = "temperature_2m,uv_index",
        @Query("temperature_unit") temperatureUnit: String,
        @Query("forecast_days") forecastDays: Int = 1,
        @Query("timezone") timezone: String = "auto"
    ): WeatherResponse
}

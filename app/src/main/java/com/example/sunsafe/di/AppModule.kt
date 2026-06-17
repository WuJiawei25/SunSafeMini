package com.example.sunsafe.di

import com.example.sunsafe.data.WeatherApi
import com.example.sunsafe.data.WeatherRepository
import com.example.sunsafe.data.WeatherRepositoryImpl
import com.example.sunsafe.viewmodel.SunSafeViewModel
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {
    single {
        Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }
    }

    single {
        val json: Json = get()
        Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/")
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    single<WeatherApi> {
        get<Retrofit>().create(WeatherApi::class.java)
    }

    single<WeatherRepository> {
        WeatherRepositoryImpl(api = get())
    }

    viewModel {
        SunSafeViewModel(repository = get())
    }
}

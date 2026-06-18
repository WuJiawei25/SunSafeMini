# SunSafe Mini

SunSafe Mini is a utility-style Android app built with Kotlin and Jetpack Compose. It gives users quick, at-a-glance outdoor safety information, including current temperature, UV index, UV risk level, and simple safety advice.

## App Idea

Daily-life activity: checking whether it is safe and comfortable to go outside.

The app focuses on one small daily task: helping the user decide whether they need sunscreen, shade, or reduced outdoor time.

## Core Features

- Main screen with current location, temperature, condition text, UV index, UV risk level, and advice.
- Refresh button to fetch the latest weather data.
- Settings screen that controls main screen content and functionality.
- Location presets: Singapore, Townsville, and Tokyo.
- Temperature unit setting: Celsius or Fahrenheit.
- UV sensitivity setting: Normal or Sensitive.
- Toggle for showing or hiding upcoming hourly UV values.
- Live network data from Open-Meteo Forecast API.

## Screens

### Main Screen

The main screen is designed for fast, at-a-glance use. It shows:

- Selected location
- Current temperature
- Feels-like temperature
- Weather condition text
- UV index
- UV risk level
- Short outdoor safety recommendation
- Optional next-hour UV data

### Settings Screen

The settings screen allows the user to control:

- Location
- Temperature unit
- UV sensitivity
- Whether hourly UV data is shown

## Architecture

The app follows MVVM architecture:

- UI layer: Jetpack Compose screens and reusable components.
- ViewModel layer: `SunSafeViewModel` manages UI state and user actions.
- Repository layer: `WeatherRepository` abstracts data access.
- Network layer: `WeatherApi` uses Retrofit to fetch data from Open-Meteo.
- Dependency injection: Koin provides Retrofit, API, Repository, and ViewModel instances.

## Technologies Used

- Kotlin
- Jetpack Compose
- Material 3
- ViewModel
- StateFlow
- Repository pattern
- Retrofit
- Kotlinx Serialization
- Kotlin Coroutines
- Koin dependency injection

## External API

The app uses the Open-Meteo Forecast API:

```text
https://api.open-meteo.com/v1/forecast
```

The request uses latitude, longitude, temperature unit, current weather variables, and hourly UV variables.

Example query:

```text
https://api.open-meteo.com/v1/forecast?latitude=1.3521&longitude=103.8198&current=temperature_2m,apparent_temperature,weather_code,is_day&hourly=temperature_2m,uv_index&temperature_unit=celsius&forecast_days=1&timezone=auto
```


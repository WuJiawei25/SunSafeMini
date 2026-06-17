package com.example.sunsafe.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.sunsafe.viewmodel.SunSafeViewModel
import org.koin.androidx.compose.koinViewModel

private enum class AppScreen {
    Main,
    Settings
}

@Composable
fun SunSafeApp(
    viewModel: SunSafeViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var currentScreen by remember { mutableStateOf(AppScreen.Main) }

    when (currentScreen) {
        AppScreen.Main -> MainScreen(
            uiState = uiState,
            onRefresh = viewModel::refreshWeather,
            onOpenSettings = { currentScreen = AppScreen.Settings }
        )

        AppScreen.Settings -> SettingsScreen(
            uiState = uiState,
            onBack = { currentScreen = AppScreen.Main },
            onLocationSelected = viewModel::selectLocation,
            onTemperatureUnitSelected = viewModel::selectTemperatureUnit,
            onUvSensitivitySelected = viewModel::selectUvSensitivity,
            onShowHourlyChanged = viewModel::setShowHourlyUv
        )
    }
}

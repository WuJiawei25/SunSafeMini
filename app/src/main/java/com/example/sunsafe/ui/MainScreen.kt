package com.example.sunsafe.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.sunsafe.ui.components.HourlyUvCard
import com.example.sunsafe.ui.components.StatusMessageCard
import com.example.sunsafe.ui.components.UvRiskCard
import com.example.sunsafe.ui.components.WeatherCard
import com.example.sunsafe.viewmodel.SunSafeUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    uiState: SunSafeUiState,
    onRefresh: () -> Unit,
    onOpenSettings: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "SunSafe Mini",
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(onClick = onOpenSettings) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Open settings"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onRefresh,
                icon = {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = null
                    )
                },
                text = { Text("Refresh") }
            )
        }
    ) { innerPadding ->
        MainContent(
            uiState = uiState,
            innerPadding = innerPadding
        )
    }
}

@Composable
private fun MainContent(
    uiState: SunSafeUiState,
    innerPadding: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Daily outdoor safety at a glance",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        if (uiState.isLoading) {
            Spacer(modifier = Modifier.height(24.dp))
            CircularProgressIndicator()
            Text("Loading latest weather data...")
        }

        uiState.errorMessage?.let { message ->
            StatusMessageCard(
                title = "Network problem",
                message = message
            )
        }

        val summary = uiState.weatherSummary
        if (summary != null) {
            WeatherCard(summary = summary)
            UvRiskCard(summary = summary)

            if (uiState.showHourlyUv) {
                HourlyUvCard(hourlyUv = summary.hourlyUv)
            }
        } else if (!uiState.isLoading) {
            StatusMessageCard(
                title = "No data yet",
                message = "Tap Refresh to load the latest UV and temperature information."
            )
        }

        Spacer(modifier = Modifier.height(80.dp))
    }
}

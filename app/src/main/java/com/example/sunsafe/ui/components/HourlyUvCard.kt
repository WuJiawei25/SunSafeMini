package com.example.sunsafe.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.sunsafe.data.model.HourlyUv

@Composable
fun HourlyUvCard(hourlyUv: List<HourlyUv>) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Next hours",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            hourlyUv.forEach { item ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = item.timeLabel)
                    Text(
                        text = "UV ${String.format("%.1f", item.uvIndex)}",
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

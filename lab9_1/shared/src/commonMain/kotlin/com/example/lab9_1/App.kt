package com.example.lab9_1
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun App() {
    MaterialTheme {
        val apiClient = remember { WeatherApiClient() }
        val coroutineScope = rememberCoroutineScope()
        var cityInput by remember { mutableStateOf("") }
        var weatherList by remember { mutableStateOf(listOf<WeatherResponse>()) }
        var errorMessage by remember { mutableStateOf<String?>(null) }

        val initialCities = listOf("Warsaw", "Saratov")

        LaunchedEffect(Unit) {
            initialCities.forEach { city ->
                try {
                    val data = apiClient.getCurrentWeather(city)
                    weatherList = weatherList + data
                } catch (e: Exception) {
                    errorMessage = e.message
                }
            }
        }

        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = cityInput,
                    onValueChange = { cityInput = it },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    if (cityInput.isNotBlank()) {
                        coroutineScope.launch {
                            try {
                                val data = apiClient.getCurrentWeather(cityInput.trim())
                                weatherList = listOf(data) + weatherList
                                cityInput = ""
                            } catch (e: Exception) {
                                errorMessage = "Город не найден"
                            }
                        }
                    }
                }) {
                    Text("Добавить")
                }
            }

            if (errorMessage != null) {
                Text(text = errorMessage!!, color = MaterialTheme.colorScheme.error)
            }

            Spacer(modifier = Modifier.height(16.dp))

            BoxWithConstraints {
                val columns = when {
                    maxWidth < 600.dp -> 1
                    maxWidth < 840.dp -> 2
                    else -> 3
                }

                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(weatherList) { weather ->
                        WeatherCard(weather)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun WeatherCard(weather: WeatherResponse) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = weather.name, style = MaterialTheme.typography.headlineSmall)
            Text(text = "${weather.main.temp}°C", style = MaterialTheme.typography.bodyLarge)
            Text(text = weather.weather.firstOrNull()?.description ?: "")
            Text(text = "Влажность: ${weather.main.humidity}%")
            Text(text = "Ветер: ${weather.wind.speed} м/с")
        }
    }
}
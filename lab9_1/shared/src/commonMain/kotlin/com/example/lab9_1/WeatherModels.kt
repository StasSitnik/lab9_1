package com.example.lab9_1

import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    val name: String,
    val main: MainData,
    val weather: List<WeatherDesc>,
    val wind: WindData
)

@Serializable
data class MainData(
    val temp: Double,
    val humidity: Int,
    val pressure: Int
)

@Serializable
data class WeatherDesc(
    val description: String,
    val icon: String
)

@Serializable
data class WindData(
    val speed: Double
)
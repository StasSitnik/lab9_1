package com.example.lab9_1

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

interface WeatherService {
    suspend fun fetchWeather(city: String): String
}

class MockWeatherService(private val shouldFail: Boolean = false) : WeatherService {
    override suspend fun fetchWeather(city: String): String {
        if (shouldFail) throw Exception("Network error")
        return "Minsk: 15°C"
    }
}

class WeatherManager(private val service: WeatherService) {
    val results = mutableListOf<String>()
    var statusMessage: String = ""

    suspend fun loadWeather(city: String) {
        try {
            val data = service.fetchWeather(city)
            results.add(data)
            statusMessage = "Success"
        } catch (e: Exception) {
            statusMessage = "Error"
        }
    }
}

class WeatherIntegrationTest {

    @Test
    fun testSuccessfulApiIntegration() = runTest {
        val service = MockWeatherService(shouldFail = false)
        val manager = WeatherManager(service)

        manager.loadWeather("Minsk")
        assertEquals(1, manager.results.size)
        assertEquals("Minsk: 15°C", manager.results.first())
        assertEquals("Success", manager.statusMessage)
    }

    @Test
    fun testFailedApiIntegration() = runTest {
        val service = MockWeatherService(shouldFail = true)
        val manager = WeatherManager(service)

        manager.loadWeather("Minsk")
        assertTrue(manager.results.isEmpty())
        assertEquals("Error", manager.statusMessage)
    }

    @Test
    fun testMultipleCitiesIntegration() = runTest {
        val service = MockWeatherService(shouldFail = false)
        val manager = WeatherManager(service)

        manager.loadWeather("Minsk")
        manager.loadWeather("Saratov")
        assertEquals(2, manager.results.size)
        assertEquals("Success", manager.statusMessage)
    }
}
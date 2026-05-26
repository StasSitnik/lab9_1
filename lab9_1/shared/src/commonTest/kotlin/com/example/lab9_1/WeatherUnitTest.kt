package com.example.lab9_1


import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

data class WeatherModel(val city: String, val temp: Int, val desc: String)

class WeatherViewModel {
    val cities = mutableListOf("Minsk", "Saratov")
    var error: String? = null

    fun addCity(city: String) {
        if (city.isBlank()) {
            error = "Empty city"
            return
        }
        cities.add(city)
        error = null
    }
}

class WeatherUnitTest {

    @Test
    fun testWeatherModelCreation() {
        val model = WeatherModel("Minsk", 20, "Ясно")
        assertEquals("Minsk", model.city)
        assertEquals(20, model.temp)
        assertEquals("Ясно", model.desc)
    }

    @Test
    fun testInitialCitiesList() {
        val viewModel = WeatherViewModel()
        assertEquals(2, viewModel.cities.size)
        assertTrue(viewModel.cities.contains("Minsk"))
    }

    @Test
    fun testAddValidCity() {
        val viewModel = WeatherViewModel()
        viewModel.addCity("Warsaw")
        assertEquals(3, viewModel.cities.size)
        assertEquals("Warsaw", viewModel.cities.last())
    }

    @Test
    fun testAddEmptyCityShowsError() {
        val viewModel = WeatherViewModel()
        viewModel.addCity("")
        assertEquals("Empty city", viewModel.error)
    }
}
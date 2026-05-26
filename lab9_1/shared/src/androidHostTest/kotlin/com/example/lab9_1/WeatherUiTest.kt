package com.example.lab9_1

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import org.junit.Rule
import org.junit.Test

class WeatherUiTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testElementsAreDisplayed() {
        composeTestRule.setContent {
            Column {
                OutlinedTextField(value = "", onValueChange = {}, label = { Text("Город") })
                Button(onClick = {}) { Text("Добавить") }
            }
        }
        composeTestRule.onNodeWithText("Город").assertExists()
        composeTestRule.onNodeWithText("Добавить").assertExists()
    }

    @Test
    fun testTextInputWorks() {
        composeTestRule.setContent {
            val text = remember { mutableStateOf("") }
            OutlinedTextField(value = text.value, onValueChange = { text.value = it }, label = { Text("Город") })
        }
        composeTestRule.onNodeWithText("Город").performTextInput("Warsaw")
        composeTestRule.onNodeWithText("Warsaw").assertExists()
    }

    @Test
    fun testButtonClickTriggersAction() {
        var clicked = false
        composeTestRule.setContent {
            Button(onClick = { clicked = true }) { Text("Добавить") }
        }
        composeTestRule.onNodeWithText("Добавить").performClick()
        assert(clicked)
    }
}package com.example.lab9_1.android

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput

class WeatherUiTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testElementsAreDisplayed() {
        composeTestRule.setContent {
            Column {
                OutlinedTextField(value = "", onValueChange = {}, label = { Text("Город") })
                Button(onClick = {}) { Text("Добавить") }
            }
        }
        composeTestRule.onNodeWithText("Город").assertExists()
        composeTestRule.onNodeWithText("Добавить").assertExists()
    }

    @Test
    fun testTextInputWorks() {
        composeTestRule.setContent {
            val text = remember { mutableStateOf("") }
            OutlinedTextField(value = text.value, onValueChange = { text.value = it }, label = { Text("Город") })
        }
        composeTestRule.onNodeWithText("Город").performTextInput("Warsaw")
        composeTestRule.onNodeWithText("Warsaw").assertExists()
    }

    @Test
    fun testButtonClickTriggersAction() {
        var clicked = false
        composeTestRule.setContent {
            Button(onClick = { clicked = true }) { Text("Добавить") }
        }
        composeTestRule.onNodeWithText("Добавить").performClick()
        assert(clicked)
    }
}
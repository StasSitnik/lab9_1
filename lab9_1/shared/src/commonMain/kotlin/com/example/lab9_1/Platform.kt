package com.example.lab9_1

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
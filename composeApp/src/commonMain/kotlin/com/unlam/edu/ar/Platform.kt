package com.unlam.edu.ar

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
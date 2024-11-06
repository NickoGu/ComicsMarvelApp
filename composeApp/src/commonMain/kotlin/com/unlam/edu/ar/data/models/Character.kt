package com.unlam.edu.ar.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Character(
    val id: Long,
    val name: String,
    val description: String,
    val thumbnailUrl: String
)

package com.unlam.edu.ar.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Comic(
    val id: Long,
    val title: String,
    val description: String,
    val thumbnailUrl: String
)

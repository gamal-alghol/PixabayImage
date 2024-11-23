package com.gamal.Pixabay.data.model

data class ImagesResponse(
    val hits: List<Hit>,
    val total: Int,
    val totalHits: Int
)
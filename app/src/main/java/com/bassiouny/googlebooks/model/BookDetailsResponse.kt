package com.bassiouny.googlebooks.model

data class BookDetailsResponse(
    val id: String,
    val publisher: String,
    val volumeInfo: BookVolumeInfo
)

data class BookVolumeInfo(
    val title: String,
    val description: String
)

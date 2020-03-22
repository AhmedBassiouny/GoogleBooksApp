package com.bassiouny.googlebooks.model

data class BooksResponse(
    val items: List<Item>?
)

data class Item(
    val id: String,
    val volumeInfo: VolumeInfo?
)

data class VolumeInfo(
    val title: String
)

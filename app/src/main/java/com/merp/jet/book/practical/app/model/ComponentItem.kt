package com.merp.jet.book.practical.app.model

data class ComponentItem(
    val id: Int,
    val image_url: String,
    val media_data: MediaData,
    val sku: String,
    val variant_type: String
)
package com.merp.jet.book.practical.app.model

data class Element(
    val component_items: List<ComponentItem>,
    val element_type: String,
    val header: String,
    val id: Int,
    val media_data: MediaDataX,
    val mobile_image_url: String,
    val sku: String,
    val style: String,
    val subheader: String,
    val tablet_image_url: String,
    val variant_types: List<String>
)
package com.merp.jet.book.practical.app.model

data class DiscoveryItem(
    val api_log_id: Int,
    val generated: String,
    val page: Page,
    val request_uuid: String,
    val success: Boolean
)
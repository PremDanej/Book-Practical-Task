package com.merp.jet.book.practical.app.model

data class EbookX(
    val book_id: Int,
    val created_at: String,
    val media_id: Int,
    val pretty_price: String,
    val price: Double,
    val properties: List<Property>,
    val release_date: Any,
    val sample_available: Boolean,
    val scriptures: Boolean,
    val sku: String,
    val subscription_plan_ids: List<String>,
    val updated_at: String,
    val user_info: UserInfo,
    val version: Int,
    val view_mode: String
)
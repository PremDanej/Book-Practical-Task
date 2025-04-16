package com.merp.jet.book.practical.app.model

data class UserInfo(
    val archived: Boolean,
    val can_buy_now: Boolean,
    val completed: Boolean,
    val expires_at: String,
    val favorite: Boolean,
    val in_library: Boolean,
    val last_position: String,
    val purchased: Boolean,
    val sample: Boolean,
    val subscribable: Boolean,
    val subscribed: Boolean
)
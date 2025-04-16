package com.merp.jet.book.practical.app.model

data class Page(
    val elements: List<Element>,
    val id: Int,
    val name: String,
    val pill_filters: List<PillFilter>
)
package com.merp.jet.book.practical.app.model

data class MediaData(
    val authors: List<Author>,
    val available_variants: AvailableVariants,
    val book_id: Int,
    val categories: List<Category>,
    val cover: Cover,
    val description: String,
    val ebook: EbookX,
    val genres: List<Any>,
    val languages: List<Language>,
    val media_id: Int,
    val product_properties: List<ProductProperty>,
    val publisher: Publisher,
    val subtitles: List<Subtitle>,
    val title: String,
    val topics: List<Topic>
)
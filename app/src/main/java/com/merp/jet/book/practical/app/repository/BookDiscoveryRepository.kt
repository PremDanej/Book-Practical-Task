package com.merp.jet.book.practical.app.repository

import com.merp.jet.book.practical.app.model.DiscoveryItem
import com.merp.jet.book.practical.app.network.BookDiscoveryApi

class BookDiscoveryRepository(
    private val service: BookDiscoveryApi,

) {
    suspend fun getDiscoveryItems(): DiscoveryItem {
        val result = service.getDiscoveryItems()
        return result
    }
}
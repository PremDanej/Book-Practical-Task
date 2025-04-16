package com.merp.jet.book.practical.app.repository

import android.content.Context
import com.merp.jet.book.practical.app.data.cache.DataStoreManager
import com.merp.jet.book.practical.app.model.DiscoveryItem
import com.merp.jet.book.practical.app.network.BookDiscoveryApi

class BookDiscoveryRepository(
    private val service: BookDiscoveryApi,
    context: Context,
) {

    private val dataStore = DataStoreManager(context)

    suspend fun getDiscoveryItems(): DiscoveryItem {
        return if (dataStore.isCacheValid()) {
            dataStore.getCachedData()
        } else {
            val freshData = service.getDiscoveryItems()
            dataStore.saveData(freshData)
            freshData
        }
    }
}
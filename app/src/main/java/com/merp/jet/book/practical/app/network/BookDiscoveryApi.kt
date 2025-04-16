package com.merp.jet.book.practical.app.network

import com.merp.jet.book.practical.app.model.DiscoveryItem
import retrofit2.http.GET

interface BookDiscoveryApi {

    @GET("testdiscovery/")
    suspend fun getDiscoveryItems(): DiscoveryItem

}
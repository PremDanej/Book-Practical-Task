package com.merp.jet.book.practical.app.network

import com.merp.jet.book.practical.app.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BookDiscoveryService {

    companion object {

        private const val BASE_URL = BuildConfig.BASE_URL

        fun getClient(): BookDiscoveryApi {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(BookDiscoveryApi::class.java)
        }
    }
}
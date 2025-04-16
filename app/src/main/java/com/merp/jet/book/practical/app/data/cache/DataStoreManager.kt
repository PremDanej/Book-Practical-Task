package com.merp.jet.book.practical.app.data.cache

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.merp.jet.book.practical.app.model.DiscoveryItem
import kotlinx.coroutines.flow.first
import java.util.concurrent.TimeUnit

val Context.dataStore by preferencesDataStore("discovery_data")

class DataStoreManager(private val context: Context) {
    private val dataKey = stringPreferencesKey("cached_data")
    private val timeKey = longPreferencesKey("last_fetch_time")

    // Saving data to DataStore
    suspend fun saveData(data: DiscoveryItem) {
        val json = Gson().toJson(data)
        context.dataStore.edit {
            it[dataKey] = json
            it[timeKey] = System.currentTimeMillis()
        }
    }

    // Getting data from DataStore
    suspend fun getCachedData(): DiscoveryItem {
        val preferences = context.dataStore.data.first()
        val cachedJson = preferences[dataKey] ?: "[]"
        Log.d("BOOKS", "getCachedData: $cachedJson")
        return Gson().fromJson(cachedJson, object : TypeToken<DiscoveryItem>() {}.type)
    }

    // Check weather data is validate for 2 hours or not.
    suspend fun isCacheValid(): Boolean {
        val prefs = context.dataStore.data.first()
        val lastFetch = prefs[timeKey] ?: 0L
        val currentTime = System.currentTimeMillis()
        Log.d("BOOKS", "isCacheValid lastFetch : -> $lastFetch")
        Log.d("BOOKS", "isCacheValid currentTime : -> $currentTime")
        Log.d("BOOKS", "isCacheValid currentTime - lastFetch : -> ${currentTime - lastFetch}")
        //return currentTime - lastFetch <= TimeUnit.MINUTES.toMillis(1) ----> Checked for 1 Minutes for testing purpose
        return currentTime - lastFetch <= TimeUnit.HOURS.toMillis(2)
    }
}
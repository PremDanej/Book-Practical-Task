package com.merp.jet.book.practical.app.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.merp.jet.book.practical.app.model.DiscoveryItem
import com.merp.jet.book.practical.app.repository.BookDiscoveryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BookDiscoveryViewModel(private val repository: BookDiscoveryRepository) : ViewModel() {

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    var isLoading by mutableStateOf(false)
    private val _item: MutableLiveData<DiscoveryItem> by lazy {
        MutableLiveData<DiscoveryItem>()
    }
    val item: LiveData<DiscoveryItem> = _item

    /*init {
        getDiscoveryItems()
    }*/

    fun getDiscoveryItems(){
        isLoading = true
        viewModelScope.launch {
            try {
                _item.value = repository.getDiscoveryItems()
                isLoading = false
                _isRefreshing.emit(false)
            } catch (e: Exception) {
                Log.e("BOOK", "Error - ViewModel ->: ${e.message}")
            }
            finally {
                isLoading = false
                _isRefreshing.emit(false)
            }
        }
    }
}
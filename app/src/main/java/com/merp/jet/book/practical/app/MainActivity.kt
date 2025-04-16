package com.merp.jet.book.practical.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.merp.jet.book.practical.app.network.BookDiscoveryService
import com.merp.jet.book.practical.app.repository.BookDiscoveryRepository
import com.merp.jet.book.practical.app.screen.home.HomeScreen
import com.merp.jet.book.practical.app.ui.theme.BookiesTheme
import com.merp.jet.book.practical.app.viewmodel.BookDiscoveryViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val service = BookDiscoveryService.getClient()

        setContent {
            val context = LocalContext.current
            val repository = BookDiscoveryRepository(service,context)
            val viewModel = BookDiscoveryViewModel(repository)
            BookiesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Surface(
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        HomeScreen(viewModel)
                    }
                }
            }
        }
    }
}
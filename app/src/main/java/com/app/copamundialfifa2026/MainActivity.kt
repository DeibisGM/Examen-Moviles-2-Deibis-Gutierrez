package com.app.copamundialfifa2026

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.app.copamundialfifa2026.navigation.AppNavHost
import com.app.copamundialfifa2026.ui.theme.CopaMundialFIFA2026Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CopaMundialFIFA2026Theme {
                AppNavHost()
            }
        }
    }
}

package com.app.copamundialfifa2026.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColors = darkColorScheme(
    primary          = PaniniGold,
    onPrimary        = Color(0xFF1A1200),
    primaryContainer = PaniniGoldDim,
    onPrimaryContainer = TextPrimary,
    secondary        = PaniniRed,
    onSecondary      = Color.White,
    background       = DarkBg,
    onBackground     = TextPrimary,
    surface          = DarkSurface,
    onSurface        = TextPrimary,
    surfaceVariant   = DarkSurface2,
    onSurfaceVariant = TextSecondary,
    outline          = Color(0xFF2E3347),
    error            = PriorityCritical,
    onError          = Color.White
)

/**
 * Forced dark theme — dynamic color disabled so the Panini brand palette is always applied
 * regardless of device wallpaper or Android version.
 */
@Composable
fun CopaMundialFIFA2026Theme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColors,
        typography  = Typography,
        content     = content
    )
}

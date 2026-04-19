package com.jefisu.portlens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jefisu.portlens.designsystem.PortLensTheme

@Composable
fun App(modifier: Modifier = Modifier) {
    PortLensTheme {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(PortLensTheme.colors.bgBase),
        )
    }
}

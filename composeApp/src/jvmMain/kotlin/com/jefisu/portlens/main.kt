package com.jefisu.portlens

import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import java.awt.Dimension

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "PortLens",
        state = WindowState(size = DpSize(1088.dp, 680.dp)),
    ) {
        DisposableEffect(window) {
            window.minimumSize = Dimension(1088, 680)
            onDispose { }
        }

        App()
    }
}

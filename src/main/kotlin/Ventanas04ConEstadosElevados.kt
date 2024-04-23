

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.useResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

/**
 * Punto de entrada para la aplicación Compose para escritorio que gestiona la visibilidad e interacción
 * entre una ventana principal y una secundaria.
 */
fun main() = application {
    val icon = BitmapPainter(useResource("sample.png", ::loadImageBitmap))
    val mainWindowState = rememberWindowState()
    val secondaryWindowState = rememberWindowState()
    var showMainWindow by remember { mutableStateOf(true) }
    var showSecondWindow by remember { mutableStateOf(false) }

    if (showMainWindow) {
        MainWindow(
            icon,
            mainWindowState,
            onClose = { showMainWindow = false },
            onClickOpenSecondWindow = {
                showMainWindow = false
                showSecondWindow = true
            }
        )
    }

    if (showSecondWindow) {
        SecondaryWindow(
            icon,
            secondaryWindowState,
            onClose = { showSecondWindow = false },
            onClickBackMainWindow = {
                showMainWindow = true
                showSecondWindow = false
            }
        )
    }

    if (!showMainWindow && !showSecondWindow) {
        exitApplication()
    }
}

/**
 * Define la ventana principal con un botón para abrir la ventana secundaria.
 * @param icon El icono de la ventana.
 * @param windowState El estado de la ventana principal.
 * @param onClose Función que se llama cuando se solicita cerrar la ventana.
 * @param onClickOpenSecondWindow Acción para abrir la ventana secundaria y cerrar la ventana actual.
 */
@Composable
fun MainWindow(
    icon: BitmapPainter,
    windowState: WindowState,
    onClose: () -> Unit,
    onClickOpenSecondWindow: () -> Unit
) {
    Window(
        onCloseRequest = onClose,
        title = "Ventana Principal",
        icon = icon,
        state = windowState
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ){
            Button(
                onClick = onClickOpenSecondWindow
            ) {
                Text("Abrir Ventana Secundaria")
            }
        }
    }
}

/**
 * Define la ventana secundaria con un botón para volver a la ventana principal.
 * @param icon El icono de la ventana.
 * @param windowState El estado de la ventana secundaria.
 * @param onClose Función que se llama cuando se solicita cerrar la ventana.
 * @param onClickBackMainWindow Acción para volver a la ventana principal y cerrar la ventana actual.
 */
@Composable
fun SecondaryWindow(
    icon: BitmapPainter,
    windowState: WindowState,
    onClose: () -> Unit,
    onClickBackMainWindow: () -> Unit
) {
    Window(
        onCloseRequest = onClose,
        title = "Ventana Secundaria",
        icon = icon,
        state = windowState
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "Este es el contenido de la ventana secundaria.",
            )
            Spacer(modifier = Modifier.size(100.dp))
            Button(onClick = onClickBackMainWindow) {
                Text("Volver a la Ventana Principal")
            }
        }
    }
}
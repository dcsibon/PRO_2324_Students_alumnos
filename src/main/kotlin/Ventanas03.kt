import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.useResource
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main() = application {
    val icon = BitmapPainter(useResource("sample.png", ::loadImageBitmap))
    val state = rememberWindowState()

    Window(
        onCloseRequest = ::exitApplication,  // Cierra la aplicación cuando esta ventana se cierra
        title = "Ventana Principal",
        icon = icon,
        state = state
    ) {
        MainContent()
    }
}

@Composable
fun MainContent() {
    var showSecondWindow by remember { mutableStateOf(false) }

    Button(onClick = { showSecondWindow = true }) {
        Text("Abrir Ventana Secundaria")
    }

    if (showSecondWindow) {
        // Abrir la ventana secundaria
        SecondWindow(onClose = { showSecondWindow = false })
    }
}

@Composable
fun SecondWindow(onClose: () -> Unit) {
    Window(
        onCloseRequest = onClose,
        title = "Ventana Secundaria"
    ) {
        Text("Este es el contenido de la ventana secundaria.")
    }
}
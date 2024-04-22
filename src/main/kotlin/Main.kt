import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState

fun main() = application {
    val windowState = WindowState(size = DpSize(1200.dp, 800.dp))

    Window(
        onCloseRequest = ::exitApplication,
        title = "Mi Login",
        state = windowState
    ) {
        Prueba2()
    }
}

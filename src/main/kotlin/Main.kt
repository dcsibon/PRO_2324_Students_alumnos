
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.rememberWindowState

fun main() = application {
    val windowState = rememberWindowState(size = DpSize(1200.dp, 800.dp))
    val icon = painterResource("sample.png")

    Window(
        onCloseRequest = ::exitApplication,
        title = "Mi Login",
        icon = icon,
        state = windowState
    ) {
        LoginScreen()
    }
}

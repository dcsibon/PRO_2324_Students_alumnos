
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberWindowState
import java.awt.Toolkit

/*
fun main() = application {
    val windowState = rememberWindowState(size = DpSize(1200.dp, 800.dp))
    val icon = painterResource("sample.png")

    Window(
        onCloseRequest = ::exitApplication,
        title = "Mi Login",
        icon = icon,
        state = windowState
    ) {
        MainScreen4()
    }
}
*/


fun main() = application {

    // Obtener las dimensiones de la pantalla
    val screenSize = Toolkit.getDefaultToolkit().screenSize
    val screenWidth = screenSize.width
    val screenHeight = screenSize.height

    // Definir el tamaño de la ventana
    val windowWidth = 1200.dp
    val windowHeight = 800.dp

    // Calcular la posición para centrar la ventana
    val positionX = (screenWidth / 2 - windowWidth.value.toInt() / 2).coerceAtLeast(0)
    val positionY = (screenHeight / 2 - windowHeight.value.toInt() / 2).coerceAtLeast(0)

    val windowState = rememberWindowState(
        size = DpSize(windowWidth, windowHeight),
        position = WindowPosition(positionX.dp, positionY.dp)
    )
    val icon = painterResource("sample.png")
    val ficheros = GestionFicheros()

    Window(
        onCloseRequest = ::exitApplication,
        title = "Mi Login",
        icon = icon,
        state = windowState
    ) {
        MainScreen4(ficheros)
    }
}


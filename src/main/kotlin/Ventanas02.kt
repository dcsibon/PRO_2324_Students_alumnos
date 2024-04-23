import androidx.compose.material.Text
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.window.Window
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.useResource
import androidx.compose.ui.window.application

fun main() = application {
    val icon = BitmapPainter(useResource("sample.png", ::loadImageBitmap))

    Window(
        onCloseRequest = ::exitApplication,
        title = "Mi Login",
        icon = icon
    ) {
        Text("Hello World!")
    }
}
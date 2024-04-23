import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
@Preview
fun MainScreen() {
    Surface(
        color = Color.LightGray,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "¡Hola interfaz gráfico!"
        )
    }
}

@Composable
@Preview
fun MainScreen2() {
    Surface(
        color = Color.LightGray,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Hi mates!!",
            style = MaterialTheme.typography.h5,
            modifier = Modifier.wrapContentSize()
        )
    }
}

@Composable
fun MainScreen3() {
    Surface(
        color = Color.LightGray,
        modifier = Modifier.fillMaxSize()
    ) {
        Surface(
            color = Color.Green,
            modifier = Modifier.wrapContentSize(Alignment.TopEnd)
        ) {
            Text(
                text = "Hi mates!!",
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(20.dp)
            )
        }
    }
}

@Composable
@Preview
fun Saludo() {
    Text(
        text = "¡Hola interfaz gráfico!"
    )
}

@Composable
@Preview
fun Saludo1(msj: String) {
    Text(
        text = "¡Hola $msj!"
    )
}

@Composable
@Preview
fun Saludo2(msj: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "¡Hola $msj!"
        )
    }
}

@Composable
@Preview
fun Prueba1() {
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Esto es una prueba :-P",
            modifier = Modifier.background(Color.Yellow).padding(10.dp)
        )
        Text(
            text = "Esto es una prueba :-P",
            modifier = Modifier.background(Color(0xFFCFF5DF)).padding(10.dp)
        )
        Text(
            text = "Esto es una prueba :-P",
            modifier = Modifier.background(Color(0xFFF4C2C2)).padding(10.dp)
        )
        Text(
            text = "Esto es una prueba :-P",
            modifier = Modifier.background(Color(0xFFA1CAF1)).padding(10.dp)
        )
    }
}

@Composable
@Preview
fun Prueba2() {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "Esto es una prueba :-P",
            modifier = Modifier.background(Color(0xFFF7F7D4)).padding(10.dp)
        )
        Text(
            text = "Esto es una prueba :-P",
            modifier = Modifier.background(Color(0xFFCFF5DF)).padding(10.dp)
        )
        Text(
            text = "Esto es una prueba :-P",
            modifier = Modifier.background(Color(0xFFF4C2C2)).padding(10.dp)
        )
        Text(
            text = "Esto es una prueba :-P",
            modifier = Modifier.background(Color(0xFFA1CAF1)).padding(10.dp)
        )
    }
}

//Centrarlo en la pantalla vertical y horizontalmente...
@Composable
@Preview
fun Prueba2_sol() {
    Box(
        modifier = Modifier.fillMaxSize().background(Color.Blue),
        contentAlignment = Alignment.Center
    ){
        Column(
            modifier = Modifier.background(Color.Black),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = "Esto es una prueba :-P",
                modifier = Modifier.background(Color(0xFFF7F7D4)).padding(10.dp)
            )
            Text(
                text = "Esto es una prueba :-P",
                modifier = Modifier.background(Color(0xFFCFF5DF)).padding(10.dp)
            )
            Text(
                text = "Esto es una prueba :-P",
                modifier = Modifier.background(Color(0xFFF4C2C2)).padding(10.dp)
            )
            Text(
                text = "Esto es una prueba :-P",
                modifier = Modifier.background(Color(0xFFA1CAF1)).padding(10.dp)
            )
        }
    }
}
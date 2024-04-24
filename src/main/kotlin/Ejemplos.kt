import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.delay
import java.io.File

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
    ) {
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

@Composable
fun MainScreen4(ficheros: IFicheros) {
    Surface(
        color = Color.LightGray,
        modifier = Modifier.fillMaxSize()
    ) {
        StudentList(ficheros)
    }
}

@Composable
@Preview
fun StudentList(ficheros: IFicheros) {
    var newStudent by remember { mutableStateOf("") }
    val studentsState = remember { mutableStateListOf<String>() }
    val focusRequester = remember { FocusRequester() }
    val maxCharacters = 10

    var showToast by remember { mutableStateOf(false) }
    var toastMessage by remember { mutableStateOf("") }

    // Carga inicial de datos desde un archivo
    LaunchedEffect(key1 = true) {  // key1 = true asegura que esto se ejecute solo una vez
        val file = File("studentList.txt")
        val loadedStudents = ficheros.leer(file)
        if (loadedStudents != null) {
            studentsState.addAll(loadedStudents)
        } else {
            toastMessage = "No se pudieron cargar los datos de los estudiantes."
            showToast = true
        }
    }

    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier.padding(end = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .focusRequester(focusRequester),
                value = newStudent,
                onValueChange = {
                    if (it.length <= maxCharacters) {
                        newStudent = it
                    }
                },
                label = { Text("New student name") },
                maxLines = 1,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = Color.White
                )
            )
            Button(
                modifier = Modifier.padding(15.dp),
                onClick = {
                    if (newStudent.isNotBlank()) {
                        studentsState.add(newStudent.trim())
                        newStudent = ""
                    }
                    focusRequester.requestFocus()
                },
            ) {
                Text(text = "Add new student")
            }
        }
        LazyColumn(
            modifier = Modifier.fillMaxHeight(0.5f).width(240.dp).background(Color.White).border(2.dp, Color.Black).padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(studentsState.size) { index ->
                StudentText(name = studentsState[index])
            }
        }
        /*
        Column(
            modifier = Modifier.fillMaxHeight(0.5f).width(240.dp).background(Color.White).border(2.dp, Color.Black).padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            for (student in studentsState) {
                StudentText(name = student)
            }
        }
        */
    }

    if (showToast) {
        Toast(message = toastMessage, onDismiss = { showToast = false })
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

@Composable
fun StudentText(name: String) {
    Text(
        text = name,
        style = MaterialTheme.typography.h5,
        modifier = Modifier.padding(10.dp)
    )
}

@Composable
fun Toast(message: String, onDismiss: () -> Unit) {
    Dialog(onCloseRequest = onDismiss) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(message)
        }
    }

    // Cierra el Toast después de 3000 milisegundos (3 segundos)
    LaunchedEffect(Unit) {
        delay(3000)
        onDismiss()
    }
}
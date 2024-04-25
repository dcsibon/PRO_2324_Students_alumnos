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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import kotlinx.coroutines.delay
import java.awt.Toolkit
import java.io.File

fun main() = application {

    // Obtener las dimensiones de la pantalla
    val screenSize = Toolkit.getDefaultToolkit().screenSize
    val screenWidth = screenSize.width
    val screenHeight = screenSize.height

    // Definir el tamaño de la ventana
    val windowWidth = 1200.dp
    val windowHeight = 800.dp

    // Calcular la posición para centrar la ventana
    val positionX = (screenWidth / 2 - windowWidth.value.toInt() / 2)
    val positionY = (screenHeight / 2 - windowHeight.value.toInt() / 2)
    val windowState = rememberWindowState(
        size = DpSize(windowWidth, windowHeight),
        position = WindowPosition(positionX.dp, positionY.dp)
    )
    val icon = painterResource("sample.png")
    val ficheros = GestionFicheros()
    val fichStudents = File("studentList.txt")

    Window(
        onCloseRequest = ::exitApplication,
        title = "My Students",
        icon = icon,
        state = windowState
    ) {
        MainWindowStudents(ficheros, fichStudents)
    }
}

@Composable
fun MainWindowStudents(
    ficheros: IFicheros,
    fichStudents: File
) {
    Surface(
        color = Color.LightGray,
        modifier = Modifier.fillMaxSize()
    ) {
        StudentList(ficheros, fichStudents)
    }
}

@Composable
@Preview
fun StudentList(
    ficheros: IFicheros,
    fichStudents: File
) {
    var newStudent by remember { mutableStateOf("") }
    val studentsState = remember { mutableStateListOf<String>() }
    val focusRequester = remember { FocusRequester() }
    val maxCharacters = 10
    var toastMessage by remember { mutableStateOf("") }

    // Carga inicial de datos desde un archivo
    LaunchedEffect(key1 = true) {  // key1 = true asegura que esto se ejecute solo una vez
        val file = File("studentList.txt")
        val loadedStudents = ficheros.leer(file)
        if (loadedStudents != null) {
            studentsState.addAll(loadedStudents)
        } else {
            toastMessage = "No se pudieron cargar los datos de los estudiantes."
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ){
        Row(
            modifier = Modifier.fillMaxSize().weight(3f),
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
        Box(
            modifier = Modifier.fillMaxSize().weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = {
                    val file = ficheros.crearFic(fichStudents.absolutePath)
                    var error = ""
                    if (file != null) {
                        for (student in studentsState) {
                            error = ficheros.escribir(file, "$student\n")
                            if (error.isNotEmpty()) {
                                break
                            }
                        }
                        if (error.isNotEmpty()) {
                            toastMessage = error
                        }
                        else {
                            toastMessage = "Fichero guardado correctamente"
                        }
                    }
                    else {
                        toastMessage = "No se pudo generar el fichero studentList.txt"
                    }
                }
            ){
                Text(text = "Guardar cambios")
            }
        }
    }

    if (toastMessage.isNotEmpty()) {
        Toast(message = toastMessage, onDismiss = { toastMessage = "" })
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
            modifier = Modifier.fillMaxSize().padding(16.dp)
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

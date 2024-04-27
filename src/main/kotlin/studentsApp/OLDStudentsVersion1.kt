package studentsApp

/*
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
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
    val ficheros = FileManagement()
    val fichStudents = File("studentList.txt")

    Window(
        onCloseRequest = ::exitApplication,
        title = "My Students",
        icon = icon,
        resizable = false,
        state = windowState
    ) {
        MainWindowStudents(ficheros, fichStudents)
    }
}

@Composable
fun MainWindowStudents(
    ficheros: IFiles,
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
    ficheros: IFiles,
    fichStudents: File
) {
    var newStudent by remember { mutableStateOf("") }
    val studentsState = remember { mutableStateListOf<String>() }
    val focusRequester = remember { FocusRequester() }
    val maxCharacters = 10
    var toastMessage by remember { mutableStateOf("") }
    val showImgDownStudents = (studentsState.size > 7)

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
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(end = 20.dp)
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
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Students: ${studentsState.size}",
                        modifier = Modifier.padding(bottom = 5.dp)
                    )
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxHeight(0.78f)
                            .width(240.dp)
                            .background(Color.White)
                            .border(2.dp, Color.Black)
                            .padding(10.dp)
                    ) {
                        items(studentsState.size) { index ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 5.dp)
                            ) {
                                StudentText(
                                    name = studentsState[index],
                                    Modifier.weight(0.8f)
                                )
                                IconButton(
                                    modifier = Modifier.weight(0.2f),
                                    onClick = { studentsState.removeAt(index) }
                                ) {
                                    Icon(Icons.Default.Delete, contentDescription = "Delete student")
                                }
                            }
                        }
                    }
                    Button(
                        onClick = { studentsState.clear() }
                    ) {
                        Text("Clear all")
                    }
                }
                if (showImgDownStudents) {
                    ImageWithTooltip(
                        tooltipText = "Use scroll down-up",
                        imagePath = "up_down_arrows.png",
                        contentDesc = "Use scroll down-up",
                        modifierImg = Modifier
                            .padding(start = 5.dp,bottom = 50.dp)
                            .width(20.dp)
                    )
                }
                else {
                    Box(
                        modifier = Modifier
                        .padding(start = 5.dp,bottom = 50.dp)
                        .size(25.dp)
                    )
                }
            }
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
fun StudentText(name: String, modifier: Modifier) {
    Text(
        text = name,
        style = MaterialTheme.typography.h5,
        modifier = modifier
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageWithTooltip(tooltipText: String, imagePath: String, contentDesc: String, modifierImg: Modifier) {
    TooltipArea(
        tooltip = {
            Box(
                modifier = Modifier
                    .background(Color(0xFFF6F9B6))
                    .border(1.dp, Color.Black)
            ) {
                Text(
                    text = tooltipText,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    ) {
        Image(
            painter = painterResource(imagePath),
            contentDescription = contentDesc,
            modifier = modifierImg
        )
    }
}

@Composable
fun Toast(message: String, onDismiss: () -> Unit) {
    Dialog(
        icon = painterResource("info_icon.png"),
        title = "Atención",
        resizable = false,
        onCloseRequest = onDismiss
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            Text(message)
        }
    }

    // Cierra el Toast después de 2 segundos
    LaunchedEffect(Unit) {
        delay(2000)
        onDismiss()
    }
}
*/
package studentsApp

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.key.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import kotlinx.coroutines.delay
import java.awt.Toolkit
import java.io.File

fun main() = application {

    val icon = painterResource("sample.png")
    val windowState = GetWindowState(
        windowWidth = 1200.dp,
        windowHeight = 800.dp
    )
    val fileManagement = FileManagement()
    val studentsFile = File("studentList.txt")

    MainWindowStudents(
        title = "My Students",
        icon = icon,
        windowState = windowState,
        resizable = false,
        fileManagement = fileManagement,
        studentsFile = studentsFile,
        onCloseMainWindow = { exitApplication() }
    )
}

@Composable
fun GetWindowState(
    windowWidth: Dp,
    windowHeight: Dp,
): WindowState {
    // Obtener las dimensiones de la pantalla
    val screenSize = Toolkit.getDefaultToolkit().screenSize
    val screenWidth = screenSize.width
    val screenHeight = screenSize.height

    // Calcular la posición para centrar la ventana
    val positionX = (screenWidth / 2 - windowWidth.value.toInt() / 2)
    val positionY = (screenHeight / 2 - windowHeight.value.toInt() / 2)

    return rememberWindowState(
        size = DpSize(windowWidth, windowHeight),
        position = WindowPosition(positionX.dp, positionY.dp)
    )
}


@Composable
fun MainWindowStudents(
    title: String,
    icon: Painter,
    windowState: WindowState,
    resizable: Boolean,
    fileManagement: IFiles,
    studentsFile: File,
    onCloseMainWindow: () -> Unit,
) {
    Window(
        onCloseRequest = onCloseMainWindow,
        title = title,
        icon = icon,
        resizable = resizable,
        state = windowState
    ) {
        MaterialTheme {
            Surface(
                color = Color.LightGray,
                modifier = Modifier.fillMaxSize()
            ) {
                StudentScreen(fileManagement, studentsFile)
            }
        }
    }
}

@Composable
@Preview
fun StudentScreen(
    fileManagement: IFiles,
    studentsFile: File,
) {
    var newStudent by remember { mutableStateOf("") }
    val studentsState = remember { mutableStateListOf<String>() }
    val focusRequester = remember { FocusRequester() }
    val maxCharacters = 10
    var toastMessage by remember { mutableStateOf("") }
    val currentToastMessage by rememberUpdatedState(toastMessage)
    val showImgScrollStudentList = remember { derivedStateOf { studentsState.size > 7 } }
    var selectedIndex by remember { mutableStateOf(-1) } // -1 significa que no hay selección

    // Carga inicial de datos desde un archivo
    LaunchedEffect(key1 = true) {  // key1 = true asegura que esto se ejecute solo una vez
        val loadedStudents = fileManagement.leer(studentsFile)
        if (loadedStudents != null) {
            studentsState.addAll(loadedStudents)
        } else {
            toastMessage = "No se pudieron cargar los datos de los estudiantes."
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxSize().weight(3f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            AddNewStudent(
                newStudent = newStudent,
                focusRequester = focusRequester,
                onNewStudentChange = {
                    if (it.length <= maxCharacters) {
                        newStudent = it
                    }
                },
                onButtonAddNewStudentClick = {
                    if (newStudent.isNotBlank()) {
                        studentsState.add(newStudent.trim())
                        newStudent = ""
                    }
                    focusRequester.requestFocus()
                }
            )
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                StudentList(
                    studentsState = studentsState,
                    selectedIndex = selectedIndex,
                    focusRequester = focusRequester,
                    onStudentSelected = { index -> selectedIndex = index },
                    onIconDeleteStudentClick = { studentsState.removeAt(it) }
                ) {
                    studentsState.clear()
                }
                ImageUpDownScroll(
                    showImgScrollStudentList = showImgScrollStudentList.value,
                )
            }
        }
        SaveChangesButton(
            modifier = Modifier.fillMaxSize().weight(1f),
            onButtonSaveChangesClick = {
                var error = ""
                val newStudentsFile = fileManagement.crearFic(studentsFile.absolutePath)
                if (newStudentsFile != null) {
                    for (student in studentsState) {
                        error = fileManagement.escribir(studentsFile, "$student\n")
                        if (error.isNotEmpty()) {
                            break
                        }
                    }
                    if (error.isNotEmpty()) {
                        toastMessage = error
                    } else {
                        toastMessage = "Fichero guardado correctamente"
                    }
                } else {
                    toastMessage = "No se pudo generar el fichero studentList.txt"
                }
            }
        )
    }

    if (toastMessage.isNotEmpty()) {
        Toast(message = currentToastMessage, onDismiss = { toastMessage = "" })
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddNewStudent(
    newStudent: String,
    focusRequester: FocusRequester,
    onNewStudentChange: (String) -> Unit,
    onButtonAddNewStudentClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(end = 20.dp)
            .onKeyEvent { event: KeyEvent ->
                if (event.key == Key.Enter) {
                    onButtonAddNewStudentClick()
                    true // Consumimos el evento
                } else {
                    false // No consumimos el evento
                }
            }
    ) {
        OutlinedTextField(
            modifier = Modifier
                .padding(bottom = 10.dp)
                .focusRequester(focusRequester),
            value = newStudent,
            onValueChange = onNewStudentChange,
            label = { Text("New student name") },
            maxLines = 1,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Color.White
            )
        )
        Button(
            modifier = Modifier.padding(15.dp),
            onClick = onButtonAddNewStudentClick,
        ) {
            Text(text = "Add new student")
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun StudentList(
    studentsState: List<String>,
    selectedIndex: Int,
    focusRequester: FocusRequester,
    onStudentSelected: (Int) -> Unit,
    onIconDeleteStudentClick: (Int) -> Unit,
    onButtonClearStudentsClick: () -> Unit,
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
                .focusRequester(focusRequester)
                .focusable()
                .onFocusChanged { focusState ->
                    if (focusState.isFocused && selectedIndex >= 0) {
                        onStudentSelected(selectedIndex)
                    }
                }
                .onKeyEvent { event ->
                    if (event.type == KeyEventType.KeyUp) {
                        when (event.key) {
                            Key.DirectionUp -> {
                                if (selectedIndex > 0) {
                                    onStudentSelected(selectedIndex - 1)
                                    true
                                } else false
                            }
                            Key.DirectionDown -> {
                                if (selectedIndex < studentsState.size - 1) {
                                    onStudentSelected(selectedIndex + 1)
                                    true
                                } else false
                            }
                            else -> false
                        }
                    } else {
                        false//Solo manejar cuando la tecla se haya levantado de la presión
                    }
                }
        ) {
            items(studentsState.size) { index ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable { onStudentSelected(index) }
                        .background(if (index == selectedIndex) colorSelected else colorUnselected)
                        .padding(horizontal = 5.dp)
                ) {
                    StudentText(
                        name = studentsState[index],
                        Modifier.weight(0.8f)
                    )
                    IconButton(
                        modifier = Modifier.weight(0.2f),
                        onClick = { onIconDeleteStudentClick(index) }
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete student")
                    }
                }
            }
        }
        Button(
            onClick = onButtonClearStudentsClick
        ) {
            Text("Clear all")
        }
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

@Composable
fun ImageUpDownScroll(
    showImgScrollStudentList: Boolean,
) {
    if (showImgScrollStudentList) {
        ImageWithTooltip(
            tooltipText = "Use scroll down-up",
            imagePath = "up_down_arrows.png",
            contentDesc = "Use scroll down-up",
            modifierImg = Modifier
                .padding(start = 5.dp, bottom = 50.dp)
                .width(20.dp)
        )
    } else {
        Box(
            modifier = Modifier
                .padding(start = 5.dp, bottom = 50.dp)
                .size(20.dp)
        )
    }
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
fun SaveChangesButton(
    modifier: Modifier,
    onButtonSaveChangesClick: () -> Unit,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = onButtonSaveChangesClick
        ) {
            Text(text = "Save changes")
        }
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

package studentsApp

import androidx.compose.runtime.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File

class StudentsViewModel(
    private val fileManagement: IFiles,
    private val studentsFile: File
) {

    companion object {
        private const val MAXCHARACTERS = 10
        private const val MAXNUMSTUDENTSVISIBLE = 7
    }

    private var _newStudent = mutableStateOf("")
    val newStudent: State<String> = _newStudent

    private val _students = mutableStateListOf<String>()
    val students: List<String> = _students

    private val _infoMessage = mutableStateOf("")
    val infoMessage: State<String> = _infoMessage

    private val _showInfoMessage = mutableStateOf(false)
    val showInfoMessage: State<Boolean> = _showInfoMessage

    private val _selectedIndex = mutableStateOf(-1) // -1 significa que no hay selección
    val selectedIndex: State<Int> = _selectedIndex

    fun addStudent() {
        if (_newStudent.value.isNotBlank()) {
            _students.add(_newStudent.value.trim())
            _newStudent.value = ""
        }
    }

    fun removeStudent(index: Int) {
        if (index in _students.indices) {
            _students.removeAt(index)
        }
    }

    fun loadStudents() {
        val loadedStudents = fileManagement.leer(studentsFile)
        if (loadedStudents != null) {
            _students.addAll(loadedStudents)
        } else {
            updateInfoMessage("No se pudieron cargar los datos de los estudiantes.")
        }
    }

    fun saveStudents() {
        var error = ""
        val newStudentsFile = fileManagement.crearFic(studentsFile.absolutePath)
        if (newStudentsFile != null) {
            for (student in students) {
                error = fileManagement.escribir(studentsFile, "$student\n")
                if (error.isNotEmpty()) {
                    break
                }
            }
            if (error.isNotEmpty()) {
                updateInfoMessage(error)
            } else {
                updateInfoMessage("Fichero guardado correctamente")
            }
        } else {
            updateInfoMessage("No se pudo generar el fichero studentList.txt")
        }
    }

    fun clearStudents() {
        _students.clear()
    }

    fun shouldShowScrollStudentListImage() = _students.size > MAXNUMSTUDENTSVISIBLE

    fun newStudentChange(name: String) {
        if (name.length <= MAXCHARACTERS) {
            _newStudent.value = name
        }
    }

    fun studentSelected(index: Int) {
        _selectedIndex.value = index
    }

    private fun updateInfoMessage(message: String) {
        _infoMessage.value = message
        _showInfoMessage.value = true
        CoroutineScope(Dispatchers.Default).launch {
            delay(2000)
            _showInfoMessage.value = false
            _infoMessage.value = ""
        }
    }

    fun showInfoMessage(show: Boolean) {
        _showInfoMessage.value = show
    }
}
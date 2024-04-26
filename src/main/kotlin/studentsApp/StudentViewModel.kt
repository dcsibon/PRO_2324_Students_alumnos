package studentsApp

import androidx.compose.runtime.mutableStateOf

class StudentViewModel() {

    private val _state = mutableStateOf("Initial State")
    val state = _state

    fun updateState(newState: String) {
        _state.value = newState
    }
}
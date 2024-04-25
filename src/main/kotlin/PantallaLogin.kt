import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

/*
@Composable
@Preview
fun LoginScreen() {

    var user by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val buttonEnabled = user.isNotBlank() && password.isNotBlank()

    MaterialTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.CenterVertically),
            modifier = Modifier.fillMaxSize()
        ) {

            OutlinedTextField(
                value = user,
                onValueChange = { user = it },
                label = { Text("Usuario") }
            )

            var passVisible by remember { mutableStateOf(false) }

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                visualTransformation = if (passVisible) VisualTransformation.None else PasswordVisualTransformation('*'),
                trailingIcon = {
                    IconToggleButton(
                        checked = passVisible,
                        onCheckedChange = { passVisible = it }
                    ) {
                        Icon(
                            imageVector = if (passVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = null
                        )
                    }
                }
            )

            Button(
                onClick = {
                    user = ""
                    password = ""
                },
                enabled = buttonEnabled
            ) {
                Text(text = "Login")
            }
        }
    }
}
*/

@Composable
@Preview
fun LoginScreen() {

    var user by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val buttonEnabled = user.isNotBlank() && password.isNotBlank()
    var passVisible by remember { mutableStateOf(false) }

    MaterialTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.CenterVertically),
            modifier = Modifier.fillMaxSize()
        ) {

            User(user) { user = it }

            Password(password, { password = it }, passVisible) { passVisible = it }

            LoginButton(buttonEnabled) {
                user = ""
                password = ""
            }
        }
    }
}

@Composable
fun User(
    user: String,
    onUserChange: (String) -> Unit
) {
    OutlinedTextField(
        value = user,
        onValueChange = onUserChange,
        label = { Text("Usuario") }
    )
}

@Composable
fun Password(
    password: String,
    onPasswordChange: (String) -> Unit,
    passVisible: Boolean,
    onPassVisibleChange: (Boolean) -> Unit
) {
    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        label = { Text("Contraseña") },
        visualTransformation = if (passVisible) VisualTransformation.None else PasswordVisualTransformation('*'),
        trailingIcon = {
            IconToggleButton(
                checked = passVisible,
                onCheckedChange = onPassVisibleChange
            ) {
                Icon(
                    imageVector = if (passVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                    contentDescription = null
                )
            }
        }
    )
}

@Composable
fun LoginButton(
    buttonEnabled: Boolean,
    onLoginButtonClick: () -> Unit
) {
    Button(
        onClick = onLoginButtonClick,
        enabled = buttonEnabled
    ) {
        Text(text = "Login")
    }
}

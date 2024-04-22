import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
@Preview
fun MyBox1() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .width(50.dp)
                .height(50.dp)
                .background(Color.Cyan)
        )
    }
}

@Preview
@Composable
fun MyBox2() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment =
    Alignment.Center) {
        Box(
            modifier = Modifier
                .width(300.dp)
                .height(200.dp)
                .background(Color.Cyan),
            contentAlignment = Alignment.BottomCenter
        ) {
            Text(
                text = "Esto es un EJEMPLO del uso de Box",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(bottom = 20.dp)
            )
        }
    }
}

@Preview
@Composable
fun MyColumn1() {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment =
    Alignment.CenterHorizontally) {
        Text("Ejemplo 1", modifier = Modifier
            .background(Color.Red)
            .weight(1f))
        Text("Ejemplo 2", modifier = Modifier
            .background(Color.Gray)
            .weight(2f))
        Text("Ejemplo 3", modifier = Modifier
            .background(Color.Cyan)
            .weight(2f))
        Text("Ejemplo 4", modifier = Modifier
            .background(Color.Green)
            .weight(1f))
    }
}

@Preview
@Composable
fun MyColumn2() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            "Ejemplo 1",
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .background(Color.Red)
                .fillMaxWidth()
                .height(400.dp)
                .wrapContentHeight(align = Alignment.CenterVertically)
        )
        Text(
            "Ejemplo 2",
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .background(Color.Gray)
                .fillMaxWidth()
                .height(400.dp)
        )
        Text(
            "Ejemplo 3",
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .background(Color.Cyan)
                .fillMaxWidth()
                .height(400.dp)
                .wrapContentHeight(align = Alignment.CenterVertically)
        )
        Text(
            "Ejemplo 4",
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .background(Color.Green)
                .fillMaxWidth()
                .height(400.dp)
                .wrapContentHeight(align = Alignment.CenterVertically)
        )
    }
}

@Preview
@Composable
fun MyRow1() {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            "Ejemplo 1", modifier = Modifier
                .height(300.dp)
                .border(2.dp, Color.Red)
                .padding(5.dp)
        )
        Text(
            "Ejemplo 2", modifier = Modifier
                .height(200.dp)
                .border(2.dp, Color.Blue)
                .padding(5.dp)
        )
        Text(
            "Ejemplo 3", modifier = Modifier
                .height(100.dp)
                .border(2.dp, Color.Red)
                .padding(5.dp)
        )
        Text(
            "Ejemplo 4", modifier = Modifier
                .border(2.dp, Color.Blue)
                .padding(5.dp)
        )
    }
}

@Preview
@Composable
fun MyExampleSpacer() {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            contentAlignment = Alignment.Center,
            modifier =
            Modifier.fillMaxWidth().weight(1f).background(Color.Cyan)
        ) {
            Text("BOX 1", fontSize = 40.sp)
        }
        Spacer(modifier = Modifier.height(50.dp))
        Box(
            contentAlignment = Alignment.Center,
            modifier =
            Modifier.fillMaxWidth().weight(1f).background(Color.LightGray)
        ) {
            Text("BOX2", fontSize = 40.sp)
        }
        Spacer(modifier = Modifier.height(100.dp))
        Box(
            contentAlignment = Alignment.Center,
            modifier =
            Modifier.fillMaxWidth().weight(1f).background(Color.Green)
        ) {
            Text("BOX3", fontSize = 40.sp)
        }
        Spacer(modifier = Modifier.height(20.dp))
        Box(
            contentAlignment = Alignment.Center,
            modifier =
            Modifier.fillMaxWidth().weight(1f).background(Color.Magenta)
        ) {
            Text("BOX4", fontSize = 40.sp)
        }
    }
}
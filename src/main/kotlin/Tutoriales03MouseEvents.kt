import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.onDrag
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.awtEventOrNull
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.singleWindowApplication

fun main() = singleWindowApplication {
    TutorialMouse09()
}

@Composable
fun TutorialMouse01() {
    var count by remember { mutableStateOf(0) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        var text by remember { mutableStateOf("Click magenta box!") }

        Column {
            @OptIn(ExperimentalFoundationApi::class)
            Box(
                modifier = Modifier
                    .background(Color.Magenta)
                    .fillMaxWidth(0.7f)
                    .fillMaxHeight(0.2f)
                    .combinedClickable(
                        onClick = {
                            text = "Click! ${++count}"
                        },
                        onDoubleClick = {
                            text = "Double click! ${++count}"
                        },
                        onLongClick = {
                            text = "Long click! ${++count}"
                        }
                    )
            )
            Text(text = text, fontSize = 40.sp)
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TutorialMouse02() {
    var color by remember { mutableStateOf(Color(0, 0, 0)) }
    Box(
        modifier = Modifier
            .wrapContentSize(Alignment.Center)
            .fillMaxSize()
            .background(color = color)
            .onPointerEvent(PointerEventType.Move) {
                val position = it.changes.first().position
                color = Color(position.x.toInt() % 256, position.y.toInt() % 256, 0)
            }
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TutorialMouse03() {
    Column(
        Modifier.background(Color.White),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        repeat(10) { index ->
            var active by remember { mutableStateOf(false) }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = if (active) Color.Green else Color.White)
                    .onPointerEvent(PointerEventType.Enter) { active = true }
                    .onPointerEvent(PointerEventType.Exit) { active = false },
                fontSize = 30.sp,
                fontStyle = if (active) FontStyle.Italic else FontStyle.Normal,
                text = "Item $index"
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TutorialMouse04() {
    var number by remember { mutableStateOf(0f) }
    Box(
        Modifier
            .fillMaxSize()
            .onPointerEvent(PointerEventType.Scroll) {
                number += it.changes.first().scrollDelta.y
            },
        contentAlignment = Alignment.Center
    ) {
        Text("Scroll to change the number: $number", fontSize = 30.sp)
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TutorialMouse05() {
    var text by remember { mutableStateOf("") }

    Box(
        Modifier
            .fillMaxSize()
            .onPointerEvent(PointerEventType.Press) {
                text = it.awtEventOrNull?.locationOnScreen?.toString().orEmpty()
            },
        contentAlignment = Alignment.Center
    ) {
        Text(text)
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TutorialMouse06() {
    val list = remember { mutableStateListOf<String>() }

    Column(
        Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        val position = event.changes.first().position
                        // on every relayout Compose will send synthetic Move event,
                        // so we skip it to avoid event spam
                        if (event.type != PointerEventType.Move) {
                            list.add(0, "${event.type} $position")
                        }
                    }
                }
            },
    ) {
        for (item in list.take(20)) {
            Text(item)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable
fun TutorialMouse07() {
    Column {
        var topBoxText by remember { mutableStateOf("Click me\nusing LMB or LMB + Shift") }
        var topBoxCount by remember { mutableStateOf(0) }
        // No indication on interaction
        Box(modifier = Modifier.size(200.dp, 100.dp).background(Color.Blue)
            // the most generic click handler (without extra conditions) should be the first one
            .onClick {
                // it will receive all LMB clicks except when Shift is pressed
                println("Click with primary button")
                topBoxText = "LMB ${topBoxCount++}"
            }.onClick(
                keyboardModifiers = { isShiftPressed } // accept clicks only when Shift pressed
            ) {
                // it will receive all LMB clicks when Shift is pressed
                println("Click with primary button and shift pressed")
                topBoxCount++
                topBoxText = "LMB + Shift ${topBoxCount++}"
            }
        ) {
            AnimatedContent(
                targetState = topBoxText,
                modifier = Modifier.align(Alignment.Center)
            ) {
                Text(text = it, textAlign = TextAlign.Center)
            }
        }

        var bottomBoxText by remember { mutableStateOf("Click me\nusing LMB or\nRMB + Alt") }
        var bottomBoxCount by remember { mutableStateOf(0) }
        val interactionSource = remember { MutableInteractionSource() }
        // With indication on interaction
        Box(modifier = Modifier.size(200.dp, 100.dp).background(Color.Yellow)
            .onClick(
                enabled = true,
                interactionSource = interactionSource,
                matcher = PointerMatcher.mouse(PointerButton.Secondary), // Right Mouse Button
                keyboardModifiers = { isAltPressed }, // accept clicks only when Alt pressed
                onLongClick = { // optional
                    bottomBoxText = "RMB Long Click + Alt ${bottomBoxCount++}"
                    println("Long Click with secondary button and Alt pressed")
                },
                onDoubleClick = { // optional
                    bottomBoxText = "RMB Double Click + Alt ${bottomBoxCount++}"
                    println("Double Click with secondary button and Alt pressed")
                },
                onClick = {
                    bottomBoxText = "RMB Click + Alt ${bottomBoxCount++}"
                    println("Click with secondary button and Alt pressed")
                }
            )
            .onClick(interactionSource = interactionSource) { // use default parameters
                bottomBoxText = "LMB Click ${bottomBoxCount++}"
                println("Click with primary button (mouse left button)")
            }
            .indication(interactionSource, LocalIndication.current)
        ) {
            AnimatedContent(
                targetState = bottomBoxText,
                modifier = Modifier.align(Alignment.Center)
            ) {
                Text(text = it, textAlign = TextAlign.Center)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TutorialMouse08() {
    val windowInfo = LocalWindowInfo.current

    Column {
        var topBoxOffset by remember { mutableStateOf(Offset(0f, 0f)) }

        Box(modifier = Modifier.offset {
            IntOffset(topBoxOffset.x.toInt(), topBoxOffset.y.toInt())
        }.size(100.dp)
            .background(Color.Green)
            .onDrag { // all default: enabled = true, matcher = PointerMatcher.Primary (left mouse button)
                topBoxOffset += it
            }
        ) {
            Text(text = "Drag with LMB", modifier = Modifier.align(Alignment.Center))
        }

        var bottomBoxOffset by remember { mutableStateOf(Offset(0f, 0f)) }

        Box(modifier = Modifier.offset {
            IntOffset(bottomBoxOffset.x.toInt(), bottomBoxOffset.y.toInt())
        }.size(100.dp)
            .background(Color.LightGray)
            .onDrag(
                enabled = true,
                matcher = PointerMatcher.mouse(PointerButton.Secondary), // right mouse button
                onDragStart = {
                    println("Gray Box: drag start")
                },
                onDragEnd = {
                    println("Gray Box: drag end")
                }
            ) {
                val keyboardModifiers = windowInfo.keyboardModifiers
                bottomBoxOffset += if (keyboardModifiers.isCtrlPressed) it * 2f else it
            }
        ) {
            Text(text = "Drag with RMB,\ntry with CTRL", modifier = Modifier.align(Alignment.Center))
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TutorialMouse09() {
    var topBoxOffset by remember { mutableStateOf(Offset(0f, 0f)) }

    Box(modifier = Modifier.offset {
        IntOffset(topBoxOffset.x.toInt(), topBoxOffset.y.toInt())
    }.size(100.dp)
        .background(Color.Green)
        .pointerInput(Unit) {
            detectDragGestures(
                matcher = PointerMatcher.Primary
            ) {
                topBoxOffset += it
            }
        }
    ) {
        Text(text = "Drag with LMB", modifier = Modifier.align(Alignment.Center))
    }
}
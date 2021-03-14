package br.com.omniplatypus.todosample.ui.todo

import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.omniplatypus.todosample.ui.theme.Green700
import br.com.omniplatypus.todosample.ui.theme.TodoSampleTheme

@Composable
fun TodoScreen(viewModel: TodoViewModel) {
    Scaffold(
        floatingActionButton = {
            AddTodoButton(
                isInAddMode = viewModel.addMode,
                onClicked = viewModel::addButtonClicked
            )
        }
    ) {
        LazyColumn {
            itemsIndexed(viewModel.todos) { index, item ->
                TodoRow(
                    index = index,
                    todo = item,
                    onItemClick = viewModel::changeCompletionStatus
                )
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AddTodoButton(
    isInAddMode: Boolean,
    onClicked: () -> Unit
) {
    FloatingActionButton(
        onClick = onClicked
    ) {
        val iconRotation = animateFloatAsState(targetValue = if (isInAddMode) -135f else 0f)
        val icon = Icons.Default.Add

        Icon(
            imageVector = icon,
            contentDescription = icon.name,
            modifier = Modifier.rotate(iconRotation.value)
        )
    }
}

@Preview
@Composable
fun AddTodoButtonPreview() {
    var addMode by mutableStateOf(false)
    AddTodoButton(
        isInAddMode = addMode,
        onClicked = { addMode = !addMode }
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TodoTextInput(
    todo: TodoItem,
    onTextChange: (String) -> Unit,
    onSendItem: (todo: TodoItem) -> Unit
) {
    Card {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val keyboardController = LocalSoftwareKeyboardController.current
            TextField (
                value = todo.text,
                onValueChange = onTextChange,
                colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
                maxLines = 1,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    onSendItem(todo)
                    keyboardController?.hideSoftwareKeyboard()
                }),
            )

            Button (onClick = {
                onSendItem(todo)
            }) {
                val icon = Icons.Default.Send
                Icon(
                    imageVector = icon,
                    contentDescription = icon.name
                )
            }
        }
    }
}

@Preview
@Composable
fun AddTodoTextInputPreview() {
    TodoTextInput(
        todo = TodoItem(text = "Todo1", done = false),
        onTextChange = {},
        onSendItem = {}
    )
}


@Composable
fun TodoRow(
    index: Int,
    todo: TodoItem,
    onItemClick: (Int) -> Unit
) {
    val color by animateColorAsState(
        targetValue = if (todo.done) Green700 else MaterialTheme.colors.onSurface
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(index) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        val iconPadding = Modifier.padding(
                start = 8.dp,
                top = 8.dp,
                bottom = 8.dp,
                end = 16.dp
            )

        Crossfade(targetState = todo.done) {
            if (it) {
                Icon(
                    imageVector = Icons.Default.Check,
                    tint = color,
                    contentDescription = todo.icon.name,
                    modifier = iconPadding
                )
            } else {
                Checkbox(
                    checked = false,
                    onCheckedChange = null,
                    modifier = iconPadding
                )
            }
        }

        Text(text = todo.text, color = color)
    }
}

@Preview("Todo - Done")
@Composable
fun TodoItemDonePreview() {
    TodoSampleTheme {
        Surface {
            TodoRow(
                index = 1,
                todo = TodoItem(
                    text = "A complete task",
                    done = true
                ),
                onItemClick = { /*TODO*/ }
            )
        }
    }
}

@Preview("Todo - Not done")
@Composable
fun TodoItemPreview() {
    TodoSampleTheme {
        Surface {
            TodoRow(
                index = 1,
                todo = TodoItem(
                    text = "An incomplete task",
                    done = false
                ),
                onItemClick = { /*TODO*/ }
            )
        }
    }
}
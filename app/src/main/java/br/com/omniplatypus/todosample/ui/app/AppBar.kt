package br.com.omniplatypus.todosample.ui.app

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.com.omniplatypus.todosample.ui.todo.TodoViewModel

@Composable
fun TodoTopBar(
    modifier: Modifier = Modifier,
    viewModel: TodoViewModel
) {
    TopAppBar (
        modifier = modifier,
        title = {
            Text(text = "Todo List")
        },
        actions = {
            ClearAllButton(viewModel = viewModel)
        }
    )
}

@Composable
fun ClearAllButton (viewModel: TodoViewModel) {
    IconButton(
        onClick = {
            viewModel.removeItem(*viewModel.todos.toTypedArray())
        },
        enabled = viewModel.todos.isNotEmpty()
    ) {
        Icon(imageVector = Icons.Default.Delete, contentDescription = "Clear list")
    }
}
package br.com.omniplatypus.todosample

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import br.com.omniplatypus.todosample.ui.theme.TodoSampleTheme
import br.com.omniplatypus.todosample.ui.todo.TodoItem
import br.com.omniplatypus.todosample.ui.todo.TodoScreen
import br.com.omniplatypus.todosample.ui.todo.TodoViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: TodoViewModel by viewModels()
        addBunchOfItems(viewModel)
        setContent {
            TodoSampleTheme {
                Surface(color = MaterialTheme.colors.background) {
                    TodoScreen(viewModel)
                }
            }
        }
    }
}

private fun addBunchOfItems(viewModel: TodoViewModel) {
    repeat(40) {
        viewModel.addItem(
            TodoItem(
                text = "Item $it",
                done = false
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TodoSampleTheme {
        val viewModel = TodoViewModel()
        TodoScreen(viewModel = viewModel)
    }
}
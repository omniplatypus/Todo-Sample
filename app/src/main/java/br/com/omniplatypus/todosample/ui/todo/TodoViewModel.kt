package br.com.omniplatypus.todosample.ui.todo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class TodoViewModel: ViewModel() {

    var addMode: Boolean by mutableStateOf(false)
        private set
    var todos: List<TodoItem> by mutableStateOf(listOf())
        private set

    fun addItem(todo: TodoItem) {
        todos = todos + todo
    }

    fun removeItem(todo: TodoItem) {
        todos = todos - todo
    }

    fun changeCompletionStatus(index: Int) {
        editItem(index) {
            it.copy(done = !it.done)
        }
    }

    fun addButtonClicked() {
        addMode = !addMode
    }

    fun changeDescription(index: Int, newDescription: String) {
        editItem(index) { it.copy(text = newDescription) }
    }

    private fun editItem(index: Int, editAction: (oldItem: TodoItem) -> TodoItem) {
        todos = todos.toMutableList().apply {
            val oldItem = this[index]
            this[index] = editAction(oldItem)
        }
    }
}
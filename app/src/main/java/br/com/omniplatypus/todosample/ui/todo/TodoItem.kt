package br.com.omniplatypus.todosample.ui.todo

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Check
import androidx.compose.ui.graphics.vector.ImageVector
import java.util.*

data class TodoItem (
    val id: UUID = UUID.randomUUID(),
    val text: String,
    val done: Boolean
) {
    val icon: ImageVector = if (done) Icons.Default.Check else Icons.Default.AccountBox
}
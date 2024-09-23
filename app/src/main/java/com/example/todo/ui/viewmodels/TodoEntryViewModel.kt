package com.example.todo.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.todo.data.models.Todo
import com.example.todo.data.repository.todo.TodoRepository

data class TodoDetails (
    val id : Int = 0,
    val title : String = "",
    val description : String = "",
    val isDone : Boolean = false
)

fun TodoDetails.toTodo(): Todo {
    return Todo(
        id = id,
        title = title,
        description = description,
        isDone = isDone
    )
}

data class TodoUiState(
    val todoDetails: TodoDetails = TodoDetails(),
    val isValid: Boolean = false

)

class TodoEntryViewModel(private val todoRepository: TodoRepository): ViewModel() {
    var todoUiState by mutableStateOf(TodoUiState())
        private set

    fun updateUiState(todoDetails: TodoDetails) {
        todoUiState = TodoUiState(todoDetails, isValid = todoDetails.title.isNotEmpty())
    }

    suspend fun insertTodo() {
        if(!todoUiState.isValid) return
        val todo = Todo(
            title = todoUiState.todoDetails.title,
            description = todoUiState.todoDetails.description,
            isDone = todoUiState.todoDetails.isDone
        )
        todoRepository.insert(todo)
    }


}
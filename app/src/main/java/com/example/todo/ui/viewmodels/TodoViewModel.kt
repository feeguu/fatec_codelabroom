package com.example.todo.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.data.models.Todo
import com.example.todo.data.repository.todo.TodoRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class TodoViewModel(savedStateHandle: SavedStateHandle, private val todoRepository: TodoRepository) : ViewModel(){
    var todoViewUiState by mutableStateOf(TodoViewUiState())
        private set

    private val todoId: Int = checkNotNull(savedStateHandle["id"])

    init {
        if(todoId != 0){
            viewModelScope.launch {
                val todo = todoRepository.getByIdStream(todoId).filterNotNull().first()
                todoViewUiState = TodoViewUiState(
                    todoDetails = TodoDetails(
                        id = todo.id,
                        title = todo.title,
                        description = todo.description,
                        isDone = todo.isDone
                    ),
                    isValid = true,
                    isEditing = false,
                )
            }
        }
    }

    fun toggleEditing(){
        todoViewUiState = todoViewUiState.copy(isEditing = !todoViewUiState.isEditing)
    }

    fun updateUiState(todoDetails: TodoDetails){
        todoViewUiState = TodoViewUiState(todoDetails, isValid = todoDetails.title.isNotEmpty(), isEditing = todoViewUiState.isEditing);
    }

    suspend fun updateTodo(){
        if(!todoViewUiState.isValid) return
        val todo = Todo(
            id = todoViewUiState.todoDetails.id,
            title = todoViewUiState.todoDetails.title,
            description = todoViewUiState.todoDetails.description,
            isDone = todoViewUiState.todoDetails.isDone
        )
        todoViewUiState = todoViewUiState.copy(isEditing = false)
        todoRepository.update(todo)
    }

    suspend fun deleteTodo(){
        todoRepository.delete(todoViewUiState.todoDetails.toTodo())
    }

}

data class TodoViewUiState (
    val todoDetails: TodoDetails = TodoDetails(),
    val isValid: Boolean = false,
    val isEditing: Boolean = false
)

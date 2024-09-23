package com.example.todo.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.data.models.Todo
import com.example.todo.data.repository.todo.TodoRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(private val todoRepository: TodoRepository): ViewModel() {

    private val _homeUiState = MutableStateFlow(HomeUiState())
    val homeUiState: StateFlow<HomeUiState> = _homeUiState

    init {
        viewModelScope.launch {
            todoRepository.getAllStream().map { HomeUiState(it) }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5000),
                    initialValue = HomeUiState()
                ).collect {
                    _homeUiState.value = it
                }
        }
    }

    fun toggleDone(todo: Todo) {
        viewModelScope.launch {
            val updatedTodo = todo.copy(isDone = !todo.isDone)
            todoRepository.update(updatedTodo)
            val updatedTodos = _homeUiState.value.todos.map {
                if (it.id == todo.id) updatedTodo else it
            }
            _homeUiState.value = HomeUiState(updatedTodos)
        }
    }
}

data class HomeUiState(var todos: List<Todo> = listOf())
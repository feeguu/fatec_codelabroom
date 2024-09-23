package com.example.todo.ui.viewmodels

import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.todo.TodoApplication
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            TodoEntryViewModel(
                todoRepository = todoApplication().appContainer.todoRepository
            )
        }
        initializer {
            HomeViewModel(
                todoRepository = todoApplication().appContainer.todoRepository
            )
        }
    }

    fun todoViewModel(todoId: Int) = viewModelFactory {
        initializer {
            TodoViewModel(
                todoRepository = todoApplication().appContainer.todoRepository,
                savedStateHandle = this.createSavedStateHandle().apply {
                    set("id", todoId)
                }
            )
        }
    }
}

fun CreationExtras.todoApplication(): TodoApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as TodoApplication)
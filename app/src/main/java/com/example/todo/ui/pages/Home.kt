package com.example.todo.ui.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todo.data.models.Todo
import com.example.todo.ui.components.TodoItem
import com.example.todo.ui.viewmodels.AppViewModelProvider
import com.example.todo.ui.viewmodels.HomeViewModel

@Composable
fun Home(onNavigateTodo: (Int) -> Unit, onNavigateCreateTodo: () -> Unit, viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)) {

    val homeUiState by viewModel.homeUiState.collectAsState()
    Scaffold(modifier = Modifier.fillMaxSize(), floatingActionButton = {
        FloatingActionButton(onClick = onNavigateCreateTodo) {
            Icon(Icons.Filled.Add, contentDescription = "Add Todo")
        }
    }) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            items(homeUiState.todos) { todo ->
                TodoItem(todo = todo, onClick = { onNavigateTodo(todo.id) }, onCheckedChange = {
                    viewModel.toggleDone(todo)
                }, isChecked = todo.isDone)
            }
        }
    }
}
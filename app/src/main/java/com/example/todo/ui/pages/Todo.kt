package com.example.todo.ui.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todo.ui.viewmodels.AppViewModelProvider
import com.example.todo.ui.viewmodels.TodoViewModel
import com.example.todo.ui.viewmodels.TodoViewUiState
import com.example.todo.ui.viewmodels.toTodo
import kotlinx.coroutines.launch

@Composable
fun StartEditButton(viewModel: TodoViewModel) {
    FloatingActionButton(onClick = { viewModel.toggleEditing() }) {
        if (viewModel.todoViewUiState.isEditing) {
            Icon(Icons.Filled.Clear, contentDescription = "Cancel")
        } else {
            Icon(Icons.Filled.Create, contentDescription = "Edit")
        }
    }
}

@Composable
fun Todo(todoId: Int, back: () -> Unit) {
    val viewModel: TodoViewModel = viewModel(factory = AppViewModelProvider.todoViewModel(todoId))
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        floatingActionButton = {
            StartEditButton(viewModel = viewModel)
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .fillMaxHeight()
        ) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
                TextField(
                    value = viewModel.todoViewUiState.todoDetails.title,
                    onValueChange = {
                        coroutineScope.launch {
                            viewModel.updateUiState(
                                viewModel.todoViewUiState.todoDetails.copy(title = it)
                            )
                        }
                    },
                    singleLine = true,
                    enabled = viewModel.todoViewUiState.isEditing,
                    textStyle = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.weight(1f),
                    colors = TextFieldDefaults.colors(
                        disabledContainerColor = MaterialTheme.colorScheme.background,
                        disabledTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer
                    )
                )
                Button(
                    modifier = Modifier
                        .padding(8.dp)
                        .background(MaterialTheme.colorScheme.primary, MaterialTheme.shapes.medium),
                    onClick = {
                        if(viewModel.todoViewUiState.isEditing){
                            coroutineScope.launch {
                                viewModel.updateTodo()
                            }
                        } else {
                            coroutineScope.launch {
                                viewModel.deleteTodo()
                                back()
                            }
                        }
                    }) {
                    if (viewModel.todoViewUiState.isEditing) {
                        Icon(imageVector = Icons.Filled.Done, contentDescription = "Done")
                    } else {
                        Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete")
                    }

                }
            }
            Spacer(modifier = Modifier.size(8.dp))
            TextField(
                value = viewModel.todoViewUiState.todoDetails.description,
                onValueChange = {
                    coroutineScope.launch {
                        viewModel.updateUiState(
                            viewModel.todoViewUiState.todoDetails.copy(description = it)
                        )
                    }
                },
                textStyle = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = TextFieldDefaults.colors(
                    disabledContainerColor = MaterialTheme.colorScheme.background,
                    disabledIndicatorColor = MaterialTheme.colorScheme.background,
                    disabledTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.background,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer
                ),
                enabled = viewModel.todoViewUiState.isEditing
            )
        }
    }
}
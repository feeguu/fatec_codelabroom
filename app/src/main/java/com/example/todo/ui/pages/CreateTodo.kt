package com.example.todo.ui.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todo.ui.viewmodels.AppViewModelProvider
import com.example.todo.ui.viewmodels.TodoEntryViewModel
import kotlinx.coroutines.launch

@Composable
fun CreateTodo(
    onBack: () -> Unit,
    viewModel: TodoEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    fun onSaveClick() {
        coroutineScope.launch {
            viewModel.insertTodo()
            onBack()
        }
    }
    Dialog(
        onDismissRequest = {
            onBack()
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(360.dp)
                .background(MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Create Todo",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.size(16.dp))
                TextField(
                    value = viewModel.todoUiState.todoDetails.title,
                    isError = !viewModel.todoUiState.isValid,
                    onValueChange = {
                        viewModel.updateUiState(
                            viewModel.todoUiState.todoDetails.copy(title = it)
                        )
                    },
                    singleLine = true,
                    label = { Text("Title") }
                )
                Spacer(modifier = Modifier.size(16.dp))
                TextField(
                    value = viewModel.todoUiState.todoDetails.description,
                    onValueChange = {
                        viewModel.updateUiState(
                            viewModel.todoUiState.todoDetails.copy(description = it)
                        )
                    },
                    label = { Text("Description") },
                    maxLines = 3,
                    minLines = 3

                )
                Spacer(modifier = Modifier.size(16.dp))
                Button(
                    onClick = {
                        onSaveClick()
                    }
                ) {
                    Text("Save")
                }
            }
        }
    }
}
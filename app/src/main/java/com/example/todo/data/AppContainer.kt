package com.example.todo.data

import android.content.Context
import com.example.todo.data.repository.todo.OfflineTodoRepository
import com.example.todo.data.repository.todo.TodoRepository

interface AppContainer {
    val todoRepository: TodoRepository
}

class AppContainerImpl(private val context: Context) : AppContainer {
    override val todoRepository by lazy {
        OfflineTodoRepository(AppDatabase.getInstance(context).todoDao())
    }
}
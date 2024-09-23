package com.example.todo.data.repository.todo

import com.example.todo.data.models.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    fun getAllStream(): Flow<List<Todo>>
    fun getByIdStream(id: Int): Flow<Todo?>
    suspend fun insert(todo: Todo)
    suspend fun update(todo: Todo)
    suspend fun delete(todo: Todo)
}
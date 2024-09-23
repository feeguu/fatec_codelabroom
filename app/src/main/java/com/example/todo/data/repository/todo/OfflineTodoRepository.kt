package com.example.todo.data.repository.todo

import com.example.todo.data.daos.TodoDao
import com.example.todo.data.models.Todo
import kotlinx.coroutines.flow.Flow

class OfflineTodoRepository(private val todoDao: TodoDao) : TodoRepository {
    override fun getAllStream(): Flow<List<Todo>> {
        return todoDao.getAll()
    }

    override fun getByIdStream(id: Int): Flow<Todo?> {
        return todoDao.getById(id)
    }

    override suspend fun insert(todo: Todo) {
        todoDao.insert(todo)
    }

    override suspend fun update(todo: Todo) {
        todoDao.update(todo)
    }

    override suspend fun delete(todo: Todo) {
        todoDao.delete(todo)
    }
}
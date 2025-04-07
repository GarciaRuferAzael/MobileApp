package com.example.todolist.data.repositories

import com.example.todolist.data.database.Todo
import com.example.todolist.data.database.TodosDAO

class TodosRepository(
    private val dao: TodosDAO
) {
    val todos = dao.getAll()

    suspend fun upsert(todo: Todo) = dao.upsert(todo)

    suspend fun delete(todo: Todo) = dao.delete(todo)
}
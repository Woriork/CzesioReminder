package com.example.czesioreminder.data

import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {
    fun getTasks() = taskDao.getTasks()
    suspend fun add(task: Task) = taskDao.insert(task)
    suspend fun update(task: Task) = taskDao.update(task)
    suspend fun delete(task: Task) = taskDao.delete(task)
    suspend fun getTaskById(id: Int) = taskDao.getTaskById(id)
    fun getTasksByCategory(category: String): Flow<List<Task>> = taskDao.getTasksByCategory(category) // Dodaj nową metodę
}
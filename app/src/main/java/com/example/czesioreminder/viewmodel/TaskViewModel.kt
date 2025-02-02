package com.example.czesioreminder.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.czesioreminder.data.Task
import com.example.czesioreminder.data.TaskDatabase
import com.example.czesioreminder.data.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TaskRepository
    private val _tasksState = MutableStateFlow<List<Task>>(emptyList())
    val tasksState: StateFlow<List<Task>> get() = _tasksState

    private val _taskState = MutableStateFlow<Task?>(null)
    val taskState: StateFlow<Task?> get() = _taskState

    init {
        val db = TaskDatabase.getDatabase(application)
        val dao = db.taskDao()
        repository = TaskRepository(dao)

        fetchTasks()
    }

    private fun fetchTasks() {
        viewModelScope.launch {
            repository.getTasks().collect { tasks ->
                _tasksState.value = tasks
            }
        }
    }

    fun addTask(task: Task) {
        viewModelScope.launch {
            repository.add(task)
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            repository.update(task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.delete(task)
        }
    }

    fun getTaskById(id: Int) {
        viewModelScope.launch {
            val task = repository.getTaskById(id)
            _taskState.value = task
        }
    }

    fun getTasksByCategory(category: String) {
        viewModelScope.launch {
            repository.getTasksByCategory(category).collect { tasks ->
                _tasksState.value = tasks
            }
        }
    }
}
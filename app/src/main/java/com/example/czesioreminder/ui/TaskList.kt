package com.example.czesioreminder.ui

import androidx.compose.foundation.background
import com.example.czesioreminder.ui.theme.CzesioReminderTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.Alignment
import androidx.compose.foundation.clickable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.czesioreminder.data.Task
import com.example.czesioreminder.viewmodel.TaskViewModel

@Composable
fun TaskList(navController: NavHostController) {
    val viewModel: TaskViewModel = viewModel()
    val tasks by viewModel.tasksState.collectAsState()

    var showCompleted by remember { mutableStateOf(false) }
    var sortByDate by remember { mutableStateOf(true) }

    val filteredTasks = tasks.filter { task ->
        if (showCompleted) task.isCompleted else true
    }.sortedWith { task1, task2 ->
        when {
            sortByDate -> task1.date.compareTo(task2.date)
            else -> task1.title.compareTo(task2.title)
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("addTask") }) {
                Icon(Icons.Default.Add, contentDescription = "Nowy Task")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFB0B59E))
        ) {
            Text(
                text = "Moje Taski",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color(0xFF565547))
                    .padding(8.dp),
                color = Color.White
            )

            LazyColumn(modifier = Modifier.padding(16.dp)) {
                items(filteredTasks) { task ->
                    TaskItem(
                        task = task,
                        onTaskClick = { navController.navigate("taskDetail/${task.id}") },
                        onCheckedChange = { isChecked ->
                            viewModel.updateTask(task.copy(isCompleted = isChecked))
                        }
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(onClick = { showCompleted = !showCompleted }) {
                    Text(if (showCompleted) "Pokaż wszystkie" else "Pokaż ukończone")
                }
                Button(onClick = { sortByDate = !sortByDate }) {
                    Text(if (sortByDate) "Sortuj po nazwie" else "Sortuj po dacie")
                }
                Button(onClick = { navController.navigate("addTask") }) {
                    Text("Nowy Task")
                }
            }
        }
    }
}

@Composable
fun TaskItem(task: Task, onTaskClick: () -> Unit, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onTaskClick() }
            .background(Color(0xFFA5A58D))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = task.title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(text = task.date, fontSize = 14.sp, fontWeight = FontWeight.Light)
            Text(text = task.category, fontSize = 14.sp)
        }
        Checkbox(
            checked = task.isCompleted,
            onCheckedChange = onCheckedChange
        )
    }
}

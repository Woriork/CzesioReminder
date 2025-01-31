package com.example.czesioreminder.ui

import android.app.DatePickerDialog
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.czesioreminder.viewmodel.TaskViewModel
import java.util.*
import androidx.compose.ui.Alignment

@Composable
fun TaskDetailScreen(taskId: Int, navController: NavHostController) {
    val context = LocalContext.current
    val viewModel: TaskViewModel = viewModel()

    LaunchedEffect(taskId) {
        viewModel.getTaskById(taskId)
    }

    val task by viewModel.taskState.collectAsState()

    if (task == null) {
        Text("Błąd: Task nie znaleziony", color = Color.Red)
        return
    }

    var selectedDate by remember { mutableStateOf(task!!.date) }

    fun showDatePicker(context: Context, onDateSelected: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        val datePicker = DatePickerDialog(context, { _, year, month, day ->
            val selectedDate = "$day.${month + 1}.$year"
            onDateSelected(selectedDate)
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))

        datePicker.show()
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFB0B59E))
        ) {
            Text(
                text = task!!.title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF565547))
                    .padding(8.dp),
                color = Color.White
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Text("Kategoria: ${task!!.category}", fontSize = 16.sp)

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Data: $selectedDate", fontSize = 16.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = {
                        showDatePicker(context) { newDate -> selectedDate = newDate }
                    }) {
                        Text("Zmień")
                    }
                }

                Spacer(Modifier.height(16.dp))
                Text("Treść: ${task!!.description}", fontSize = 16.sp)

                Spacer(Modifier.height(16.dp))
                Text("Status", fontWeight = FontWeight.Bold)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(onClick = { navController.popBackStack() }) { Text("Cofnij") }
                    Button(onClick = { navController.navigate("editTask/${task!!.id}") }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edytuj")
                        Text(" Edytuj")
                    }
                    Button(
                        onClick = {
                            viewModel.deleteTask(task!!)
                            navController.popBackStack()
                            Toast.makeText(context, "Task usunięty", Toast.LENGTH_SHORT).show()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = "Usuń")
                        Text(" Usuń")
                    }
                }
            }
        }
    }
}
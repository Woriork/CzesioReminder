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
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import com.example.grades.R

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
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Zdjęcie jako tło
            Image(
                painter = painterResource(id = R.drawable.background_image), // Dodaj swoje zdjęcie do folderu res/drawable
                contentDescription = "Tło",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
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

                // Dodanie białego, przezroczystego prostokąta pod tekstem "Treść", "Kategoria", "Data" i "Status"
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xD0FFFFFF)) // 0xD0FFFFFF to biały kolor z 80% przezroczystością
                        .padding(16.dp)
                ) {
                    Column {
                        Text("Kategoria: ${task!!.category}", fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(8.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Data: $selectedDate", fontSize = 16.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(onClick = {
                                showDatePicker(context) { newDate -> selectedDate = newDate }
                            }) {
                                Text("Zmień")
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text("Treść: ${task!!.description}", fontSize = 16.sp)

                        Spacer(modifier = Modifier.height(16.dp))

                        Text("Status", fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(Modifier.height(16.dp))

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
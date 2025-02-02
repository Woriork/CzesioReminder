package com.example.czesioreminder.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.czesioreminder.data.Task
import com.example.czesioreminder.viewmodel.TaskViewModel
import com.example.czesioreminder.viewmodel.TaskViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.platform.LocalContext
import android.app.Application
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.compose.ui.graphics.Brush

@Composable
fun AddTask(navController: NavHostController, modifier: Modifier = Modifier) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("General") }
    var date by remember { mutableStateOf("2023-10-01") }
    var newCategory by remember { mutableStateOf("") } // Dodane dla nowej kategorii
    var expanded by remember { mutableStateOf(false) } // Dodane dla rozwijanej listy

    // Lista dostępnych kategorii (można ją przechowywać w ViewModel)
    val categories = remember { mutableStateListOf("General", "Work", "Home", "Shopping") }

    val context = LocalContext.current
    val viewModel: TaskViewModel = viewModel(
        LocalViewModelStoreOwner.current!!,
        "TaskViewModel",
        TaskViewModelFactory(LocalContext.current.applicationContext as Application)
    )

    fun showDatePicker(context: Context, onDateSelected: (String) -> Unit) {
        val calendar = java.util.Calendar.getInstance()
        val datePicker = android.app.DatePickerDialog(
            context,
            { _, year, month, day ->
                val selectedDate = "$day.${month + 1}.$year"
                onDateSelected(selectedDate)
            },
            calendar.get(java.util.Calendar.YEAR),
            calendar.get(java.util.Calendar.MONTH),
            calendar.get(java.util.Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color(0xFFB0B59E), Color(0xFF565547))
                        )
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    Text(
                        text = "Dodaj nowe zadanie",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFF565547))
                            .padding(8.dp),
                        color = Color.White
                    )

                    Column(modifier = Modifier.padding(16.dp)) {
                        OutlinedTextField(
                            value = title,
                            onValueChange = { title = it },
                            label = { Text("Tytuł") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedTextField(
                            value = description,
                            onValueChange = { description = it },
                            label = { Text("Opis") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        // Rozwijana lista kategorii
                        Box {
                            Button(
                                onClick = { expanded = true },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Kategoria: $category")
                            }
                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                categories.forEach { cat ->
                                    DropdownMenuItem(
                                        text = { Text(cat) },
                                        onClick = {
                                            category = cat
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Dodawanie nowej kategorii
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            OutlinedTextField(
                                value = newCategory,
                                onValueChange = { newCategory = it },
                                label = { Text("Nowa kategoria") },
                                modifier = Modifier.weight(1f)
                            )
                            Button(
                                onClick = {
                                    if (newCategory.isNotBlank() && !categories.contains(newCategory)) {
                                        categories.add(newCategory)
                                        category = newCategory
                                        newCategory = ""
                                    }
                                }
                            ) {
                                Text("Dodaj")
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Data: $date", fontSize = 16.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(onClick = {
                                showDatePicker(context) { newDate -> date = newDate }
                            }) {
                                Text("Zmień datę")
                            }
                        }
                    }
                }
            }
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = { navController.popBackStack() }) {
                    Text("Odrzuć zmiany")
                }
                Button(onClick = {
                    val task = Task(0, title, description, category, date)
                    viewModel.addTask(task)
                    navController.popBackStack()
                }) {
                    Text("Zatwierdź zmiany")
                }
            }
        }
    )
}
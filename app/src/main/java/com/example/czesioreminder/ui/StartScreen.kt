package com.example.czesioreminder.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.grades.R




@Composable
fun StartScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFB0B59E))
    ) {
        // Pasek z nazwą aplikacji
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF565547))
                .padding(16.dp)
        ) {
            Text(
                text = "CzesioReminder",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterStart)
            )
        }

        // Główna zawartość ekranu
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Obraz po lewej
            Image(
                painter = painterResource(id = R.drawable.image), // Upewnij się, że masz obraz w folderze res/drawable
                contentDescription = "Obraz Czesława",
                modifier = Modifier
                    .width(190.dp)
                    .height(482.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Tekst i przyciski po prawej
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Dzień dobry!",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Jestem rycerzem,",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "rycerzem",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Czesławem, smukła dziewico",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(32.dp))

                // Przyciski
                Button(
                    onClick = { navController.navigate("taskList") },
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Zaczynamy",
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        android.os.Process.killProcess(android.os.Process.myPid())
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Paszą won",
                        color = Color.White
                    )
                }

            }
        }
    }
}
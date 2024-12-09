package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigation()
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable("signup") { SignUpScreen(navController) }
        composable("document") { DocumentScreen(navController) }
        composable("semester/{semester}") { backStackEntry ->
            val semester = backStackEntry.arguments?.getString("semester")
            SemesterScreen(semester ?: "Unknown", navController)
        }
        composable("subjects/{semester}/{subject}") { backStackEntry ->
            val subject = backStackEntry.arguments?.getString("subject")
            SubjectScreen(subject ?: "Unknown", navController)
        }
        composable("mid_term") { MidTermScreen(navController) }
        composable("final_term") { FinalTermScreen(navController) }
    }
}

@Composable
fun LoginScreen(navController: NavHostController) {
    var CNICNO by remember { mutableStateOf("") }
    var StudentID by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(19.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Rana Arif",
            color = Color.Blue,
            fontSize = 39.sp
        )

        OutlinedTextField(
            value = CNICNO,
            onValueChange = { CNICNO = it },
            label = { Text("Enter Your Name", color = Color.Blue) }
        )
        Spacer(Modifier.height(25.dp))

        OutlinedTextField(
            value = StudentID,
            onValueChange = { StudentID = it },
            label = { Text("Roll no", color = Color.Blue) }
        )
        Spacer(Modifier.height(25.dp))

        Button(
            onClick = {
                if (CNICNO == "Rana Arif" && StudentID == "45") {
                    message = "Accepted"
                    navController.navigate("document")
                } else {
                    message = "No record found"
                }
            },
            colors = ButtonDefaults.buttonColors(Color.Blue),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 35.dp)
        ) {
            Text("Sign in")
        }

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = {
                navController.navigate("signup")
            },
            colors = ButtonDefaults.buttonColors(Color.Blue),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 35.dp)
        ) {
            Text("Sign up")
        }

        Spacer(Modifier.height(25.dp))

        if (message.isNotEmpty()) {
            Text(text = message, color = Color.Red)
        }
    }
}

@Composable
fun SignUpScreen(navController: NavHostController) {
    var name by remember { mutableStateOf("") }
    var rollNo by remember { mutableStateOf("") }
    var program by remember { mutableStateOf("") }
    var session by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(26.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Sign Up", color = Color.Blue)

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Enter Your Name", color = Color.Blue) }
        )
        Spacer(Modifier.height(20.dp))

        OutlinedTextField(
            value = rollNo,
            onValueChange = { rollNo = it },
            label = { Text("Roll no", color = Color.Blue) }
        )
        Spacer(Modifier.height(25.dp))

        OutlinedTextField(
            value = program,
            onValueChange = { program = it },
            label = { Text("Program", color = Color.Blue) }
        )
        Spacer(Modifier.height(19.dp))

        OutlinedTextField(
            value = session,
            onValueChange = { session = it },
            label = { Text("Session", color = Color.Blue) }
        )
        Spacer(Modifier.height(19.dp))

        Button(
            onClick = {
                if (name.isNotEmpty() && rollNo.isNotEmpty() && program.isNotEmpty() && session.isNotEmpty()) {
                    navController.navigate("document")
                } else {
                    message = "Please fill all fields"
                }
            },
            colors = ButtonDefaults.buttonColors(Color.Blue),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 35.dp)
        ) {
            Text("Submit")
        }

        Spacer(Modifier.height(25.dp))

        if (message.isNotEmpty()) {
            Text(text = message, color = Color.Red)
        }
    }
}

@Composable
fun DocumentScreen(navController: NavHostController) {
    val semesters = (1..8).map { "Semester $it" }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Select Semester",
            color = Color.Blue,
            fontSize = 24.sp,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        semesters.forEach { semester ->
            Button(
                onClick = {
                    navController.navigate("semester/$semester")
                },
                colors = ButtonDefaults.buttonColors(Color.Blue),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(text = semester, fontSize = 18.sp)
            }
        }
    }
}

@Composable
fun SemesterScreen(semester: String, navController: NavHostController) {
    val subjects = when (semester) {
        "Semester 1" -> listOf(
            "Functional English", "Programming", "DLDs", "Discrete Structure", "ICTs"
        )
        "Semester 2" -> listOf(
            "OOP", "Database", "Software Engineering", "Expository Writing", "Statistics"
        )
        "Semester 3" -> listOf(
            "Civics", "Assembly Language", "Professional Practice", "Data Structure",
            "Cyber Security", "Applied Physics", "Ideology and Constitution of Pakistan"
        )
        "Semester 4" -> listOf(
            "subject"
        )
        "Semester 5" -> listOf(
            "subject"
        )
        "Semester 6" -> listOf(
            "Subject"
        )
        "Semester 7" -> listOf(
            "subject"
        )
        "Semester 8" -> listOf(
            "subject"
        )
        else -> emptyList()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(18.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Select Subject for $semester",
            color = Color.Blue,
            fontSize = 25.sp,
            modifier = Modifier.padding(vertical = 18.dp)
        )

        subjects.forEach { subject ->
            Button(
                onClick = {
                    navController.navigate("subjects/$semester/$subject")
                },
                colors = ButtonDefaults.buttonColors(Color.Blue),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
            ) {
                Text(text = subject, fontSize = 18.sp)
            }
        }
    }
}

@Composable
fun SubjectScreen(subject: String, navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(18.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Subject: $subject",
            color = Color.Blue,
            fontSize = 25.sp
        )

        Spacer(Modifier.height(18.dp))

        Button(
            onClick = { navController.navigate("mid_term") },
            colors = ButtonDefaults.buttonColors(Color.Blue),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Mid Term")
        }

        Spacer(Modifier.height(18.dp))

        Button(
            onClick = { navController.navigate("final_term") },
            colors = ButtonDefaults.buttonColors(Color.Blue),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Final Term")
        }
    }
}

@Composable
fun MidTermScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Mid Term Folders",
            color = Color.Blue,
            fontSize = 24.sp,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        Button(
            onClick = { /* Navigate to Books and Outline */ },
            colors = ButtonDefaults.buttonColors(Color.Blue),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(text = "Books and Outline", fontSize = 18.sp)
        }

        Button(
            onClick = { /* Navigate to Notes */ },
            colors = ButtonDefaults.buttonColors(Color.Blue),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(text = "Notes", fontSize = 18.sp)
        }
    }
}

@Composable
fun FinalTermScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Final Term Folders",
            color = Color.Blue,
            fontSize = 24.sp,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        Button(
            onClick = { /* Navigate to Books and Outline */ },
            colors = ButtonDefaults.buttonColors(Color.Blue),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(text = "Books and Outline", fontSize = 18.sp)
        }

        Button(
            onClick = { /* Navigate to Notes */ },
            colors = ButtonDefaults.buttonColors(Color.Blue),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(text = "Notes", fontSize = 18.sp)
        }
    }
}

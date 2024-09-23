package com.example.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import com.example.todo.data.models.Todo
import com.example.todo.ui.components.TodoItem
import com.example.todo.ui.pages.CreateTodo
import com.example.todo.ui.pages.Home
import com.example.todo.ui.pages.Todo
import com.example.todo.ui.theme.TodoTheme
import kotlinx.serialization.Serializable


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App()
        }
    }
}

@Serializable
object HomeScreen

@Serializable
data class TodoScreen(val id: Int);

@Serializable
object CreateTodoScreen

@Composable
fun App() {


    val navController = rememberNavController()

    TodoTheme {
        NavHost(navController = navController, startDestination = HomeScreen) {
            composable<HomeScreen> {
                Home(onNavigateTodo = { todoId ->
                    navController.navigate(TodoScreen(todoId))
                }, onNavigateCreateTodo = {
                    navController.navigate(CreateTodoScreen)
                })
            }
            composable<TodoScreen> { backStackEntry ->
                val todoId = backStackEntry.arguments?.getInt("id") ?: 0
                Todo(todoId, back = {
                    navController.popBackStack()
                })
            }

            dialog<CreateTodoScreen> {
                CreateTodo(onBack = {
                    navController.popBackStack()
                })
            }

        }
    }
}


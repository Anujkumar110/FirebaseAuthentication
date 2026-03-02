package com.example.firebaseauthentication

import LoginScreen
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*

@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()

    val authState = authViewModel.authState.observeAsState()

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthViewModel.AuthState.Authenticated -> {
                navController.navigate("home") {
                    popUpTo(0)
                }
            }
            is AuthViewModel.AuthState.Unauthenticated -> {
                navController.navigate("login") {
                    popUpTo(0)
                }
            }
            else -> Unit
        }
    }

    NavHost(navController, startDestination = "login") {

        composable("login") {
            LoginScreen(navController, authViewModel)
        }

        composable("register") {
            RegisterScreen(navController, authViewModel)
        }

        composable("home") {
            HomeScreen(authViewModel)
        }
    }
}
package com.example.composenavigationapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composenavigationapp.ui.theme.ComposeNavigationAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeNavigationAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "padaria") {
        composable("padaria") {
            PadariaScreen(navController = navController)
        }
        composable("restaurante?pedido={pedido}") {
            val pedido = it.arguments?.getString("pedido")
            RestauranteScreen(navController = navController, pedido = pedido)
        }
    }
}

@Composable
fun PadariaScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Bem-vindo à Padaria!")
        Text(text = "O que deseja levar para o restaurante?")
        
        Button(
            modifier = Modifier.padding(top = 16.dp),
            onClick = { navController.navigate("restaurante?pedido=Cesta de Pães") }
        ) {
            Text("Levar Cesta de Pães")
        }
        
        Button(
            modifier = Modifier.padding(top = 8.dp),
            onClick = { navController.navigate("restaurante?pedido=Bolo de Fubá") }
        ) {
            Text("Levar Bolo de Fubá")
        }

        Button(
            modifier = Modifier.padding(top = 8.dp),
            onClick = { navController.navigate("restaurante") }
        ) {
            Text("Ir sem nada")
        }
    }
}

@Composable
fun RestauranteScreen(navController: NavController, pedido: String?) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Bem-vindo ao Restaurante!")
        
        if (pedido != null) {
            Text(
                text = "Você trouxe da padaria: $pedido",
                modifier = Modifier.padding(vertical = 16.dp)
            )
        } else {
            Text(
                text = "Você chegou de mãos vazias!",
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }
        
        Button(onClick = { navController.popBackStack() }) {
            Text("Voltar para a Padaria")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PadariaPreview() {
    ComposeNavigationAppTheme {
        PadariaScreen(rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
fun RestaurantePreview() {
    ComposeNavigationAppTheme {
        RestauranteScreen(rememberNavController(), "Pão de Queijo")
    }
}

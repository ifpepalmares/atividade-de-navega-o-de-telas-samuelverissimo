package com.example.composenavigationapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.composenavigationapp.ui.theme.ComposeNavigationAppTheme

// Data class para os produtos
data class Produto(
    val nome: String,
    val imageUrl: String
)

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
            PadariaScreen()
        }
        composable("restaurante?pedido={pedido}") {
            val pedido = it.arguments?.getString("pedido")
            RestauranteScreen(navController = navController, pedido = pedido)
        }
    }
}

@Composable
fun PadariaScreen() {
    // Lista fictícia de produtos com URLs reais de imagens
    val produtos = listOf(
        Produto("Pão Francês", "https://i.pinimg.com/736x/a0/6e/01/a06e01638007441e573808d1ffc92073.jpg"),
        Produto("Croissant", "https://i.pinimg.com/736x/b3/c2/61/b3c261b1e5f02d393fb6bb3ebbb7c9ae.jpg"),
        Produto("Bolo de Chocolate", "https://i.pinimg.com/736x/dd/79/ca/dd79ca03d0495a8b4fecc4f963b3fbbe.jpg"),
        Produto("Sonho", "https://i.pinimg.com/736x/36/f1/a3/36f1a3cd7c97f24bdde7f5e639ef85e0.jpg")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Logo da Padaria carregada do drawable
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo da Padaria",
            modifier = Modifier.size(120.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Padaria Delícia",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Lista de Produtos
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(produtos) { produto ->
                ProductItem(produto)
            }
        }
    }
}

@Composable
fun ProductItem(produto: Produto) {
    var isChecked by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Checkbox para selecionar o produto
            Checkbox(
                checked = isChecked,
                onCheckedChange = { isChecked = it }
            )

            Spacer(modifier = Modifier.width(8.dp))

            // Imagem do Produto
            AsyncImage(
                model = produto.imageUrl,
                contentDescription = produto.nome,
                modifier = Modifier.size(80.dp),
                contentScale = ContentScale.Crop
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Text(
                text = produto.nome,
                style = MaterialTheme.typography.bodyLarge
            )
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
        Text(text = "🍽️ Bem-vindo ao Restaurante!", style = MaterialTheme.typography.headlineMedium)
        
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
        PadariaScreen()
    }
}

package com.example.idioms

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.School

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToTutorial: () -> Unit,
    navigateToGame: () -> Unit,
    navigateToEditWord: () -> Unit,
    navigateToAddWord: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = {}) {
                            Icon(imageVector = Icons.Default.NightsStay, contentDescription = "Modo Noche")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        TextField(
                            value = "",
                            onValueChange = {},
                            modifier = Modifier.weight(1f),
                            placeholder = { Text("Buscar") },
                            leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "Buscar") }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "1200", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.width(8.dp))
                        IconButton(onClick = {}) {
                            Icon(imageVector = Icons.Default.FilterList, contentDescription = "Filtrar")
                        }
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Spacer(modifier = Modifier.weight(1f)) // Empuja el botón "Jugar" al centro
                    Button(
                        onClick = {navigateToGame()},
                        modifier = Modifier.weight(1f)// Centra el botón dentro del Row
                    ) {
                        Text(text = "Jugar")
                    }
                    Spacer(modifier = Modifier.weight(1f)) // Espacio entre el botón y el icono
                    IconButton(onClick = {navigateToTutorial()}) {
                        Icon(imageVector = Icons.Default.School, contentDescription = "Tutorial")
                    }
                }
            }
        }

    ) { paddingValues ->
        val words = listOf(
            WordItem("Palabra 1", "Traducción 1", "Pronunciación 1", "Asociación 1"),
            WordItem("Palabra 1", "Traducción 1", "Pronunciación 1", "Asociación 1"),
            WordItem("Palabra 1", "Traducción 1", "Pronunciación 1", "Asociación 1"),
            WordItem("Palabra 1", "Traducción 1", "Pronunciación 1", "Asociación 1"),
            WordItem("Palabra 1", "Traducción 1", "Pronunciación 1", "Asociación 1"),
            WordItem("Palabra 1", "Traducción 1", "Pronunciación 1", "Asociación 1"),
            WordItem("Palabra 1", "Traducción 1", "Pronunciación 1", "Asociación 1"),
            WordItem("Palabra 1", "Traducción 1", "Pronunciación 1", "Asociación 1"),
            WordItem("Palabra 1", "Traducción 1", "Pronunciación 1", "Asociación 1"),
            WordItem("Palabra 2", "Traducción 2", "Pronunciación 2", "Asociación 2")
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            items(words) { word ->
                WordCard(navigateToEditWord, word)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun WordCard(navigateToEditWord: () -> Unit, word: WordItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = {navigateToEditWord()}) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Editar")
                }
                IconButton(onClick = {}) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar")
                }
            }
            Text(text = word.palabra, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(text = word.traduccion, fontSize = 16.sp)
            Text(text = word.pronunciacion, fontSize = 16.sp)
            Text(text = word.asociacion, fontSize = 16.sp)
        }
    }
}

data class WordItem(val palabra: String, val traduccion: String, val pronunciacion: String, val asociacion: String)

/*@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}*/

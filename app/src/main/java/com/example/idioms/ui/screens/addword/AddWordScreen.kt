package com.example.idioms.ui.screens.addword

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddWordScreen(navigateBack:() -> Unit/*navController: NavController*/) {
    val context = LocalContext.current

    // Datos para las listas desplegables
    val clases = listOf("Clase 1", "Clase 2", "Clase 3")
    val idiomas = listOf("Español", "Inglés", "Francés", "Alemán")

    // Variables para los campos de texto
    var palabra by remember { mutableStateOf("") }
    var traduccion by remember { mutableStateOf("") }
    var pronunciacion by remember { mutableStateOf("") }
    var asociacion by remember { mutableStateOf("") }
    var claseSeleccionada by remember { mutableStateOf(clases[0]) }
    var idiomaOrigen by remember { mutableStateOf(idiomas[0]) }
    var idiomaTraduccion by remember { mutableStateOf(idiomas[1]) }
    var palabraClaveA by remember { mutableStateOf("") }
    var palabraClaveB by remember { mutableStateOf("") }
    var palabraClaveC by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        IconButton(
                            onClick = { navigateBack() },
                            modifier = Modifier.align(Alignment.CenterStart)
                        ) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                        Text(
                            text = "Agregar palabra",
                            modifier = Modifier.align(Alignment.Center),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                OutlinedTextField(
                    value = palabra,
                    onValueChange = { palabra = it },
                    label = { Text("Palabra") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = traduccion,
                    onValueChange = { traduccion = it },
                    label = { Text("Traducción") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = pronunciacion,
                    onValueChange = { pronunciacion = it },
                    label = { Text("Pronunciación") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = asociacion,
                    onValueChange = { asociacion = it },
                    label = { Text("Asociación") },
                    modifier = Modifier.fillMaxWidth()
                )
                DropdownMenu(
                    expanded = true,
                    onDismissRequest = {},
                    content = {
                        clases.forEach { clase ->
                            DropdownMenuItem(onClick = { claseSeleccionada = clase }) {
                                Text(clase)
                            }
                        }
                    }
                )
                OutlinedTextField(
                    value = claseSeleccionada,
                    onValueChange = {},
                    label = { Text("Clase") },
                    modifier = Modifier.fillMaxWidth()
                )
                DropdownMenu(
                    expanded = true,
                    onDismissRequest = {},
                    content = {
                        idiomas.forEach { idioma ->
                            DropdownMenuItem(onClick = { idiomaOrigen = idioma }) {
                                Text(idioma)
                            }
                        }
                    }
                )
                OutlinedTextField(
                    value = idiomaOrigen,
                    onValueChange = {},
                    label = { Text("Idioma de Origen") },
                    modifier = Modifier.fillMaxWidth()
                )
                DropdownMenu(
                    expanded = true,
                    onDismissRequest = {},
                    content = {
                        idiomas.forEach { idioma ->
                            DropdownMenuItem(onClick = { idiomaTraduccion = idioma }) {
                                Text(idioma)
                            }
                        }
                    }
                )
                OutlinedTextField(
                    value = idiomaTraduccion,
                    onValueChange = {},
                    label = { Text("Idioma de Traducción") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = palabraClaveA,
                    onValueChange = { palabraClaveA = it },
                    label = { Text("Palabra clave A") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = palabraClaveB,
                    onValueChange = { palabraClaveB = it },
                    label = { Text("Palabra clave B") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = palabraClaveC,
                    onValueChange = { palabraClaveC = it },
                    label = { Text("Palabra clave C") },
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = {
                        // Agregar lógica para agregar la palabra
                        Toast.makeText(context, "Palabra agregada", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Text("Agregar Palabra")
                }
            }
        }
    )
}

fun DropdownMenuItem(onClick: () -> Unit, interactionSource: @Composable () -> Unit) {

}

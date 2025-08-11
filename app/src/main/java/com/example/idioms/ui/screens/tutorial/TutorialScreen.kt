package com.example.idioms.ui.screens.tutorial

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TutorialScreen( navigateBack:() -> Unit/*onBackClick: () -> Unit, onContinueClick: () -> Unit*/) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        IconButton(
                            onClick = {navigateBack()},
                            modifier = Modifier.align(Alignment.CenterStart)
                        ) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                        Text(
                            text = "Tutorial",
                            modifier = Modifier.align(Alignment.Center),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Descripción", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text(text = "Esta app ayuda a aprender idiomas mediante asociaciones inverosímiles, " +
                            "lo que facilita la memorización de palabras. En lugar de solo mostrar la palabra y su traducción, " +
                            "permite crear conexiones absurdas o visuales entre ambas, haciéndolas más memorables.")
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = "Pasos", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text(text = "Agregar una palabra: El usuario ingresa una palabra en el idioma que está aprendiendo.\n" +
                            "Añadir la traducción: Se introduce su significado en el idioma nativo.\n" +
                            "Crear una asociación: La app sugiere o permite al usuario escribir una conexión inverosímil entre ambas palabras.\n" +
                            "Guardar y repasar: Se guarda la asociación para repasarla más tarde mediante tarjetas o juegos interactivos.")
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = "Ejemplo", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text(text = "Palabra en inglés: \"Carrot\" (zanahoria).\n" +
                            "Asociación inverosímil: Imagina a un \"carro\" con ruedas de zanahoria.\n" +
                            "Imagen mental: Un auto deportivo con zanahorias gigantes en lugar de llantas.")
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Text(text = "Continue")
            }
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun TutorialScreenPreview() {
    TutorialScreen(/*onBackClick = {}, onContinueClick = {}*/)
}*/

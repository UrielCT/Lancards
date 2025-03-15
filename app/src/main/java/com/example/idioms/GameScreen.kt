package com.example.idioms

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.util.Date

enum class Estado {
 STOPPED, RUNNING, FINISHED
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(navigateBack:() -> Unit) {
    var estado by remember { mutableStateOf(Estado.STOPPED) }
    var correctas by remember { mutableStateOf(0) }
    var orden by remember { mutableStateOf("Mas Nuevas") }
    //var seccion by remember { mutableStateOf("Todas") }
    var juego by remember { mutableStateOf("Recordar traducción") }
    var palabra by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }
    var clase by remember { mutableStateOf("") }
    var traduccion by remember { mutableStateOf("") }
    var idiomaOrigen by remember { mutableStateOf("") }
    var idiomaTraduccion by remember { mutableStateOf("") }
    var pronunciacion by remember { mutableStateOf("") }
    var palabraClaveA by remember { mutableStateOf("") }
    var palabraClaveB by remember { mutableStateOf("") }
    var palabraClaveC by remember { mutableStateOf("") }

    var isCheckEnabled by remember { mutableStateOf(true) }
    var isNextEnabled by remember { mutableStateOf(false) }
    var isCorrect by remember { mutableStateOf<Boolean?>(null) }
    var textColor by remember { mutableStateOf<Color>(Color.Black) }

    var index by remember { mutableStateOf(0) }
    var cantidadCorrectas by remember { mutableStateOf(0) }


    // trae lista de palabras
    val palabras = listOf(
        Word("Gato", "Cat", "kæt", "Un gato atrapado en una red", "Español", "Inglés", "Verbo", Date(1640995200000)), // 1 enero 2022
        Word("Perro", "Dog", "dɔg", "Un perro con un hueso", "Español", "Inglés", "Verbo", Date(1672531200000)), // 1 enero 2023
        Word("Casa", "House", "haʊs", "Una casa con un tejado rojo", "Español", "Alemán", "Sustantivo", Date(1704067200000)), // 1 enero 2024
        Word("Manzana", "Apple", "ˈæp.l̩", "Una manzana roja brillante", "Español", "Ruso", "Adjetivo", Date(1711929600000)), // 1 abril 2024
        Word("Reloj", "Clock", "klɑːk", "Un reloj con agujas grandes", "Español", "Ruso", "Verbo", Date(1714608000000)) // 1 mayo 2024
    )




    val originList = listOf("Todos") + palabras.map { it.idiomaOrigen }.distinct()
    val tradList = listOf("Todos") + palabras.map { it.idiomaTrad }.distinct()
    val claseList = listOf("Todas") + palabras.map { it.clase }.distinct()


    val ordenList = listOf("Mas Nuevas","Mas Antiguas","Fecha Random", "Alfabético", "Alfabético Inverso", )

    val juegoList = listOf("Recordar traducción", "Recordar palabra")



    var filteredWords = remember(idiomaOrigen, idiomaTraduccion, clase, orden) {
        palabras.filter {
            (idiomaOrigen.isEmpty() || idiomaOrigen == "Todos" || it.idiomaOrigen == idiomaOrigen) &&
                    (idiomaTraduccion.isEmpty() || idiomaTraduccion == "Todos" || it.idiomaTrad == idiomaTraduccion) &&
                    (clase.isEmpty() || clase == "Todas" || it.clase == clase)
        }.let { listaFiltrada ->
            when (orden) {
                "Mas Nuevas" -> listaFiltrada.sortedByDescending { it.fecha }
                "Mas Antiguas" -> listaFiltrada.sortedBy { it.fecha }
                "Fecha Random" -> listaFiltrada.shuffled()
                "Alfabético" -> listaFiltrada.sortedBy { it.palabra }
                "Alfabético Inverso" -> listaFiltrada.sortedByDescending { it.palabra }
                else -> listaFiltrada
            }
        }

    }

    val cantList = remember(filteredWords) {
        cantidad = 0.toString()
        (1..filteredWords.size).map { it.toString() }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        IconButton(
                            onClick = { navigateBack()},
                            modifier = Modifier.align(Alignment.CenterStart)
                        ) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                        Text(
                            text = "Juego de palabras",
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
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                when (estado) {
                    Estado.STOPPED -> {


                        Column {

                            DropdownMenuBox(
                                label = "Idioma de origen",
                                options = originList,
                                selectedOption = idiomaOrigen,
                                onOptionSelected = { idiomaOrigen = it },
                                enabled = true
                            )
                            DropdownMenuBox(
                                label = "Idioma de traducción",
                                options = tradList,
                                selectedOption = idiomaTraduccion,
                                onOptionSelected = { idiomaTraduccion = it },
                                enabled = true
                            )
                            DropdownMenuBox(
                                label = "Clase",
                                options = claseList,
                                selectedOption = clase,
                                onOptionSelected = { clase = it },
                                enabled = true
                            )
                            DropdownMenuBox(
                                label = "Orden",
                                options = ordenList,
                                selectedOption = orden,
                                onOptionSelected = { orden = it },
                                enabled = true
                            )
                            DropdownMenuBox(
                                label = "Cantidad",
                                options = cantList,
                                selectedOption = cantidad,
                                onOptionSelected = { cantidad = it },
                                enabled = filteredWords.isNotEmpty()
                            )

                            DropdownMenuBox(
                                label = "Juego",
                                options = juegoList,
                                selectedOption = juego,
                                onOptionSelected = { juego = it },
                                enabled = true
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Palabras filtradas: " +
                                        filteredWords.joinToString { it.palabra },
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                    onClick = {
                                        filteredWords = filteredWords.take(cantidad.toInt())
                                        estado = Estado.RUNNING
                                              },
                                    enabled = filteredWords.isNotEmpty() && cantidad > 0.toString()
                            ) {
                                Text("Play")
                            }
                        }

                    }

                    Estado.RUNNING -> {

                        // hacer condicional segun el Juego
                        // acumular las correctas
                        // poner boton para mostrar una pista

                        Text(
                            text = "${index + 1} / ${filteredWords.size}",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color =  Color.Blue
                        )
                        Spacer(modifier = Modifier.height(16.dp))


                        Text(
                            text= getText(index, filteredWords.size, filteredWords[index].palabra),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color =  textColor
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedTextField(
                            value = traduccion,
                            onValueChange = { traduccion = it },
                            label = { Text("Traduce aquí") })

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Button(
                                onClick = {
                                    if(traduccion == filteredWords[index].traduccion){
                                        correctas++
                                        textColor = Color.Green
                                    }else {
                                        // Agregar a una lista de palabras para practicar
                                        textColor = Color.Red
                                    }
                                    isCheckEnabled = false
                                    isNextEnabled = true
                                },
                                enabled = isCheckEnabled
                            ) {
                                Text("Check")
                            }


                            Button(
                                onClick = {
                                    traduccion = ""
                                    if (index < filteredWords.size - 1) {
                                        textColor = Color.Black
                                        index++
                                        isCheckEnabled = true
                                        isNextEnabled = false
                                    } else {
                                        textColor = Color.Black
                                        estado = Estado.FINISHED
                                        index = 0
                                        isCheckEnabled = true
                                        isNextEnabled = false
                                    }
                                },
                                enabled = isNextEnabled
                            ) {
                                Text("Next")
                            }

                        }
                    }

                    Estado.FINISHED -> {
                        Text("Correctas: $correctas")
                        Text("Orden: $orden")
                        //Text("Sección: $seccion")
                        Text("Juego: $juego")

                        Text("Correctas: $correctas",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold)
                        Button(onClick = {
                            correctas = 0
                            estado = Estado.STOPPED
                        }) {
                            Text("Again")
                        }

                    }
                }


            }
        }
    )
}

fun getText(index: Int, size: Int, palabra: String):String {
    return if (index < size) palabra else "Fin"
}

fun setColor(isCorrect: Boolean?): Color {
   return when (isCorrect) {
       true -> Color.Green
       false -> Color.Red
       null -> Color.Black

   }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuBox(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    enabled: Boolean
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        painter = painterResource(id = android.R.drawable.arrow_down_float),
                        contentDescription = "Expand"
                    )
                }
            },
            enabled = enabled,
            modifier = Modifier.menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}


data class Word(
    val palabra: String,
    val traduccion: String,
    val pronunciacion: String,
    val asociacion: String,
    val idiomaOrigen:String,
    val idiomaTrad:String,
    val clase:String,
    val fecha: Date

)


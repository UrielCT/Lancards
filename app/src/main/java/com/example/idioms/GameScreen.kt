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
import java.util.Locale

enum class Estado {
 STOPPED, RUNNING, FINISHED
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(navigateBack:() -> Unit) {
    var estado by remember { mutableStateOf(Estado.STOPPED) }
    var correctas by remember { mutableStateOf(0) }
    var orden by remember { mutableStateOf("Mas Nuevas") }
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
    var palabrasFinales = listOf<Word>()

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
                                text = "Palabras: " +
                                        filteredWords.size,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                    onClick = {
                                        palabrasFinales = filteredWords
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
                            text = getText(juego, index, filteredWords.size, filteredWords[index].palabra, filteredWords[index].traduccion),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = textColor
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = getCorrectText(juego, isCorrect, filteredWords[index].palabra, filteredWords[index].traduccion),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Green
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        //si juego es 1- poner palabra,si es 2- poner traduccion
                        OutlinedTextField(
                            value = if (juego == "Recordar traducción") traduccion else palabra,
                            onValueChange = {
                                val sanitizedText = it.replace("\n", "").replace("\r", "") // Elimina saltos de línea
                                if (juego == "Recordar traducción") traduccion = sanitizedText else palabra = sanitizedText
                            },
                            label = { Text("Ingresá la forma correcta") }
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Button(
                                onClick = {
                                    if(juego == "Recordar traducción"){
                                        if(traduccion.replace(" ", "").uppercase() == filteredWords[index].traduccion.replace(" ", "").uppercase()){
                                            correctas++
                                            isCorrect = true
                                            textColor = Color.Green
                                        }else {
                                            // Agregar a una lista de palabras para practicar
                                            isCorrect = false
                                            textColor = Color.Red
                                        }
                                    }else if(juego == "Recordar palabra"){
                                        if(palabra.replace(" ", "").uppercase() == filteredWords[index].palabra.replace(" ", "").uppercase()){
                                            correctas++
                                            isCorrect = true
                                            textColor = Color.Green
                                        }else {
                                            isCorrect = false
                                            // Agregar a una lista de palabras para practicar
                                            textColor = Color.Red
                                        }
                                    }
                                    // poner if para compara los textos segun el juego
                                    // poner los dos textos en mayuscula para comparar

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
                                    palabra = ""
                                    isCorrect = null
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


                        Text(
                            text = "Correctas: $correctas de ${filteredWords.size}",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Orden: $orden",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Juego: $juego",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(16.dp))

                        Button(onClick = {
                            filteredWords = palabrasFinales
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

fun getText(juego: String, index: Int, size: Int, palabra: String, traduccion: String): String {
    return if (index < size) {
        if (juego == "Recordar traducción") palabra else traduccion
    } else {
        "Fin"
    }
}


fun getCorrectText(juego: String, isCorrect: Boolean?, palabra: String, traduccion: String): String {
   return if (juego == "Recordar traducción"){
       if(isCorrect == false) traduccion else ""
   } else {
       if(isCorrect == false) palabra else ""
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


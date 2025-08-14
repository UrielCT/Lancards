package com.example.idioms.utils

import com.example.idioms.ui.models.Word
import java.util.Date

//val words = listOf(
//    Word("Palabra 1", "Traducción 1", "Pronunciación 1", "Asociación 1Asociación 1Asociación 1Asociación 1Asociación 1Asociación 1Asociación 1Asociación 1Asociación 1Asociación 1"),
//    Word("Palabra 1", "Traducción 1", "Pronunciación 1", "Asociación 1"),
//    Word("Palabra 1", "Traducción 1", "Pronunciación 1", "Asociación 1"),
//    Word("Palabra 1", "Traducción 1", "Pronunciación 1", "Asociación 1"),
//    Word("Palabra 1", "Traducción 1", "Pronunciación 1", "Asociación 1"),
//    Word("Palabra 1", "Traducción 1", "Pronunciación 1", "Asociación 1"),
//    Word("Palabra 1", "Traducción 1", "Pronunciación 1", "Asociación 1"),
//    Word("Palabra 1", "Traducción 1", "Pronunciación 1", "Asociación 1"),
//    Word("Palabra 1", "Traducción 1", "Pronunciación 1", "Asociación 1"),
//    Word("Palabra 2", "Traducción 2", "Pronunciación 2", "Asociación 2")
//)

val words = listOf(
    Word("Gato", "Cat", "kæt", "Un gato atrapado en una red", originalLang = "Español", translatedLang = "Inglés", category = "Verbo", date =  Date(1640995200000)), // 1 enero 2022
    Word("Perro", "Dog", "dɔg", "Un perro con un hueso", originalLang = "Español", translatedLang = "Alemán", category ="Verbo",date =  Date(1672531200000)), // 1 enero 2023
    Word("Casa", "House", "haʊs", "Una casa con un tejado rojo", originalLang = "Español", translatedLang = "Ruso", category ="Sustantivo",date =  Date(1704067200000)), // 1 enero 2024
    Word("Manzana", "Apple", "ˈæp.l̩", "Una manzana roja brillante", originalLang = "Inglés", translatedLang = "Español", category ="Adjetivo",date =  Date(1711929600000)), // 1 abril 2024
    Word("Reloj", "Clock", "klɑːk", "Un reloj con agujas grandes", originalLang = "Rumano", translatedLang = "Alemán", category ="Verbo",date =  Date(1714608000000)) // 1 mayo 2024
)
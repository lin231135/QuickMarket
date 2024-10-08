package com.example.quickmarket

// Clase de datos para los libros
data class Book(
    val id: String,  // Agregar un campo id
    val title: String,
    val state: String,
    val price: String,
    val rating: Float,
    val image: Int
)

// Lista de libros predefinidos
val bookList = listOf(
    Book("1", "Matemáticas 1", "Estado: Como nuevo", "Q540.00", 5.0f, R.drawable.matematica1),
    Book("2", "Álgebra de Baldor", "Estado: Usado", "Q200.00", 5.0f, R.drawable.baldor),
    Book("3", "Cálculo 1", "Estado: Usado", "Q200.00", 5.0f, R.drawable.calculo1),
    Book("4", "Cálculo de una variable", "Estado: Usado", "Q200.00", 5.0f, R.drawable.calculode1variable)
)
package com.example.quickmarket.data.model

data class User(
    val email: String,
    val username: String,
    val password: String,
    val uploadedProducts: List<String> = emptyList(), // IDs de productos subidos
    val purchasedProducts: List<String> = emptyList() // IDs de productos comprados
)
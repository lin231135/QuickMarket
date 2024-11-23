package com.example.quickmarket.data.model

data class Product(
    var id: String = "", // Se asignará manualmente después de guardar en Firebase
    val name: String = "", // Nombre del producto
    val price: String = "", // Precio del producto
    val description: String = "", // Descripción del producto
    val imageUrl: String = "", // URL de la imagen del producto
    val sellerId: String = "", // ID del usuario que subió el producto
    val buyerId: String? = null // ID del usuario que compró el producto (null si no ha sido comprado)
)
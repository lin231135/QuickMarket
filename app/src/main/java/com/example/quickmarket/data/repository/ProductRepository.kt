package com.example.quickmarket.data.repository

import android.util.Log
import com.example.quickmarket.data.model.Product
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FieldValue
import kotlinx.coroutines.tasks.await

class ProductRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val productCollection = firestore.collection("products")
    private val userCollection = firestore.collection("users")

    /**
     * Obtener todos los productos desde Firestore.
     */
    suspend fun getProducts(): List<Product> {
        return try {
            val snapshot = productCollection.get().await()
            snapshot.documents.mapNotNull { document ->
                document.toObject(Product::class.java)?.apply { this.id = document.id }
            }
        } catch (e: Exception) {
            Log.e("ProductRepository", "Error al obtener productos: ${e.message}", e)
            emptyList()
        }
    }

    /**
     * Obtener los detalles de un producto por su ID.
     */
    suspend fun getProductById(productId: String): Product? {
        return try {
            val document = productCollection.document(productId).get().await()
            if (document.exists()) {
                document.toObject(Product::class.java)?.apply { this.id = document.id }
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("ProductRepository", "Error al obtener producto: ${e.message}", e)
            null
        }
    }

    /**
     * Agregar un producto y asociarlo a un usuario.
     */
    suspend fun addProductWithUser(userId: String, product: Product): Result<Unit> {
        return try {
            val documentReference = productCollection.add(product).await()
            val productId = documentReference.id

            productCollection.document(productId).update("id", productId, "sellerId", userId).await()

            val userRef = userCollection.document(userId)
            userRef.get().await().let { document ->
                if (!document.exists()) {
                    userRef.set(mapOf("uploadedProducts" to listOf(productId))).await()
                } else {
                    userRef.update("uploadedProducts", FieldValue.arrayUnion(productId)).await()
                }
            }

            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("ProductRepository", "Error al agregar producto: ${e.message}", e)
            Result.failure(e)
        }
    }

    /**
     * Registrar la compra de un producto (agregar al carrito).
     */
    suspend fun addToCart(userId: String, productId: String): Result<Unit> {
        return try {
            if (userId.isBlank()) {
                throw IllegalArgumentException("El userId no puede estar vacío.")
            }

            productCollection.document(productId).update("buyerId", userId).await()

            val userRef = userCollection.document(userId)
            userRef.get().await().let { document ->
                if (!document.exists()) {
                    userRef.set(mapOf("purchasedProducts" to listOf(productId))).await()
                } else {
                    userRef.update("purchasedProducts", FieldValue.arrayUnion(productId)).await()
                }
            }

            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("ProductRepository", "Error al registrar compra: ${e.message}", e)
            Result.failure(e)
        }
    }

    /**
     * Obtener los productos del carrito para un usuario específico.
     */
    suspend fun getCartProducts(userId: String): List<Product> {
        return try {
            val userDoc = userCollection.document(userId).get().await()
            val cartProductIds = userDoc["purchasedProducts"] as? List<String> ?: emptyList()

            if (cartProductIds.isEmpty()) return emptyList()

            val productSnapshots = productCollection.whereIn("id", cartProductIds).get().await()
            productSnapshots.documents.mapNotNull { document ->
                document.toObject(Product::class.java)?.apply { this.id = document.id }
            }
        } catch (e: Exception) {
            Log.e("ProductRepository", "Error al obtener productos del carrito: ${e.message}", e)
            emptyList()
        }
    }

    suspend fun getPurchasedProducts(userId: String): List<Product> {
        return try {
            // Obtén los IDs de los productos comprados
            val userDocument = firestore.collection("users").document(userId).get().await()
            val purchasedProductIds = userDocument["purchasedProducts"] as? List<String> ?: emptyList()

            // Obtén los detalles de cada producto por su ID
            val products = purchasedProductIds.mapNotNull { productId ->
                val productSnapshot = productCollection.document(productId).get().await()
                productSnapshot.toObject(Product::class.java)?.apply { id = productSnapshot.id }
            }
            products
        } catch (e: Exception) {
            Log.e("ProductRepository", "Error al obtener productos comprados: ${e.message}", e)
            emptyList()
        }
    }
}
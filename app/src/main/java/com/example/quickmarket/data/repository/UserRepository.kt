package com.example.quickmarket.data.repository

import com.example.quickmarket.data.model.User

class UserRepository {

    fun getUser(email: String): User? {
        return if (email == "test@example.com") {
            User(email, "testUser", "password123")
        } else {
            null
        }
    }
}
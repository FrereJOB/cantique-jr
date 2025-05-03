package com.jesusrevient.cantique.models

data class User(
    val uid: String = "",
    val email: String = "",
    val displayName: String = "",
    val isAdmin: Boolean = false
)

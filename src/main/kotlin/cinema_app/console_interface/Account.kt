package org.example.cinema_app.console_interface

import kotlinx.serialization.Serializable

@Serializable
data class Account(
    val login: String,
    val password: String
)
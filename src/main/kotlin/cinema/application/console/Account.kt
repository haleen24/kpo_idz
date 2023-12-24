package cinema.application.console

import kotlinx.serialization.Serializable

@Serializable
data class Account(
    val login: String,
    val password: String
)
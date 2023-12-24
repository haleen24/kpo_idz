package cinemaApplication.consoleInterface

import kotlinx.serialization.Serializable

@Serializable
data class Account(
    val login: String,
    val password: String
)
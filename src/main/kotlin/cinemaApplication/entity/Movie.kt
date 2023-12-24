package cinemaApplication.entity

import kotlinx.serialization.Serializable

@Serializable
data class Movie(var name: String, var description: String)
package cinemaApplication.entity

import kotlinx.serialization.Serializable

@Serializable
data class Session(val movieName: String, var sessionTime: Time, val cinemaHall: CinemaHall)
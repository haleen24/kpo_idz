package cinemaApplication.entity

import kotlinx.serialization.Serializable

@Serializable
class CinemaHall {

    private var seats = emptyList<Place>()

    fun creation(rows: Int, places: Int) {

        val creationList = MutableList<Place?>(size = rows * places, init = { null })

        var id = 0

        for (i in 1..rows) {

            for (j in 1..places) {

                creationList[id++] = Place(i, j, isBought = false, isOccupied = false)

            }

        }

        @Suppress("UNCHECKED_CAST")
        seats = creationList as List<Place>
    }

    fun findSeat(row: Int, seat: Int): Place? = seats.find { it.row == row && it.place == seat }

    fun getAllSeats() = seats

}
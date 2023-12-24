package cinema_app.dao

import cinema_app.entity.Movie
import cinema_app.entity.Place
import cinema_app.entity.Session
import cinema_app.entity.Time

interface CinemaDao {

    fun getSession(filter: (Session) -> Boolean = { true }): List<Session>

    fun getMovies(): List<Movie>

    fun findMovie(name: String): Movie?

    fun sellTicket(session: Session, place: Place): Boolean

    fun refundTicket(session: Session, place: Place): Boolean

    fun changeTime(session: Session, time: Time): Unit

    fun changeDescription(movie: Movie, description: String): Unit

    fun addMovie(movie: Movie): Boolean

    fun deleteMovie(movie: Movie): Boolean

    fun addSession(movie: Movie, time: Time): Boolean

    fun deleteSession(session: Session): Boolean

    fun takePlace(session: Session, place: Place): Boolean
}
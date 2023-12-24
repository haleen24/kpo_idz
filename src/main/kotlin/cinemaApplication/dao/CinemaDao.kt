package cinemaApplication.dao

import cinemaApplication.entity.Movie
import cinemaApplication.entity.Place
import cinemaApplication.entity.Session
import cinemaApplication.entity.Time


// интерфейс приложения кинотеатра
interface CinemaDao {

    fun getSession(filter: (Session) -> Boolean = { true }): List<Session>

    fun getMovies(): List<Movie>

    fun findMovie(name: String): Movie?

    fun sellTicket(session: Session, place: Place): Boolean

    fun refundTicket(session: Session, place: Place): Boolean

    fun changeTime(session: Session, time: Time)

    fun changeDescription(movie: Movie, description: String)

    fun addMovie(movie: Movie): Boolean

    fun deleteMovie(movie: Movie): Boolean

    fun addSession(movie: Movie, time: Time): Boolean

    fun deleteSession(session: Session): Boolean

    fun takePlace(session: Session, place: Place): Boolean
}
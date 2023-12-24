package cinema_app.dao

import cinema_app.entity.*

class CinemaAppDao(
    private var movieDataBase: MovieDao,

    private var sessionDataBase: SessionDao,

    private val hallRows: Int,

    private val hallSeats: Int

) : CinemaDao {

    override fun getSession(filter: (Session) -> Boolean): List<Session> =
        sessionDataBase.getSessions().filter { filter(it) }

    override fun getMovies(): List<Movie> = movieDataBase.getMovies()


    override fun findMovie(name: String): Movie? = movieDataBase.getMovieByName(name)

    override fun sellTicket(session: Session, place: Place): Boolean = sessionDataBase.buyTicket(session, place)

    override fun refundTicket(session: Session, place: Place): Boolean = sessionDataBase.refundTicket(session, place)

    override fun changeTime(session: Session, time: Time): Unit = sessionDataBase.changeTime(session, time)

    override fun changeDescription(movie: Movie, description: String): Unit =
        movieDataBase.changeDescription(movie, description)

    override fun addMovie(movie: Movie) = movieDataBase.addMovie(movie)

    override fun deleteMovie(movie: Movie): Boolean =
        movieDataBase.deleteMovie(movie) && sessionDataBase.deleteSession { it.movieName == movie.name }

    override fun addSession(movie: Movie, time: Time): Boolean =
        if (movie in movieDataBase.getMovies()) sessionDataBase.addSession(

            Session(movie.name, time, CinemaHall()).also { it.cinemaHall.creation(hallRows, hallSeats) }

        ) else false

    override fun deleteSession(session: Session): Boolean = sessionDataBase.deleteSession(session)

    override fun takePlace(session: Session, place: Place): Boolean = sessionDataBase.takePlace(session, place)
}
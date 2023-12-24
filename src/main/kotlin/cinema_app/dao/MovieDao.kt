package cinema_app.dao

import cinema_app.entity.Movie

// интерфейс объекта, который отвечает за фильмы
interface MovieDao {

    fun getMovies(): List<Movie>

    fun addMovie(movie: Movie): Boolean

    fun changeDescription(movie: Movie, description: String): Unit

    fun getMovieByName(name: String): Movie?

    fun deleteMovie(movie: Movie): Boolean
}

package cinema_app.dao

import cinema_app.entity.Movie

interface MovieDao {

    fun getMovies(): List<Movie>

    fun addMovie(movie: Movie): Boolean

    fun changeDescription(movie: Movie, description: String): Unit

    fun getMovieByName(name: String): Movie?

    fun deleteMovie(movie: Movie): Boolean
}

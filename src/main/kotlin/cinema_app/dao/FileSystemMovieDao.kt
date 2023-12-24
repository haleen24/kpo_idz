package org.example.cinema_app.dao

import cinema_app.dao.MovieDao
import cinema_app.entity.Movie
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

@Serializable
class FileSystemMovieDao(private var path: String) : MovieDao {

    private var movies: MutableSet<Movie>

    init {

        val file = File(path)

        if (!file.exists()) {

            file.createNewFile()

        }
        movies = try {

            Json.decodeFromString(file.readLines().joinToString(separator = ""))

        } catch (serializeException: SerializationException) {

            mutableSetOf()

        } catch (serializeException: IllegalArgumentException) {

            mutableSetOf()

        }

    }


    override fun getMovies(): List<Movie> = movies.toList()

    override fun addMovie(movie: Movie) = movies.add(movie).also { saveMovies() }

    override fun changeDescription(movie: Movie, description: String) {

        movie.description = description

        saveMovies()
    }

    override fun getMovieByName(name: String): Movie? = movies.find { it.name == name }

    override fun deleteMovie(movie: Movie) = movies.remove(movie).also { saveMovies() }

    fun saveMovies() = File(path).writeText(Json.encodeToString(movies))
}
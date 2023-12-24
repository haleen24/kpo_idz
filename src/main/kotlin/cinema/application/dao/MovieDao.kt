package org.example.cinema.application.dao

import org.example.cinema.application.entity.Movie

// интерфейс объекта, который отвечает за фильмы
interface MovieDao {

    fun getMovies(): List<Movie>

    fun addMovie(movie: Movie): Boolean

    fun changeDescription(movie: Movie, description: String)

    fun getMovieByName(name: String): Movie?

    fun deleteMovie(movie: Movie): Boolean
}

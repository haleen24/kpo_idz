package org.example

import cinema_app.dao.*
import org.example.cinema_app.console_interface.CinemaConsoleApplication
import org.example.cinema_app.dao.FileSystemMovieDao


fun main() {
    val rows = 2
    val seats = 2

    val movieDB: MovieDao = FileSystemMovieDao("MovieDB.json")

    val sessionDB: SessionDao = FileSystemSessionDao("SessionDB.json")

    val cinema: CinemaDao = CinemaAppDao(movieDB, sessionDB, rows, seats)

    val cinemaInterface = CinemaConsoleApplication(cinema)

    cinemaInterface.start()
}
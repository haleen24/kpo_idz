package org.example

import cinema_app.dao.*
import org.example.cinema_app.console_interface.CinemaConsoleApplication
import org.example.cinema_app.dao.FileSystemMovieDao
import kotlin.io.path.Path


fun main() {
    // количество рядов и кресел, регулируется здесь
    val rows = 2
    val seats = 2

    // база данных фильмов
    val movieDB: MovieDao = FileSystemMovieDao(Path("data", "MovieDB.json").toString())

    // база данных сеансов
    val sessionDB: SessionDao = FileSystemSessionDao(Path("data", "SessionDB.json").toString())

    // интерфейс кинотеатра
    val cinema: CinemaDao = CinemaAppDao(movieDB, sessionDB, rows, seats)

    // консольное приложение
    val cinemaInterface = CinemaConsoleApplication(cinema)

    // старт консольного приложения
    cinemaInterface.start(Path("data", "Accounts.json").toString())
}
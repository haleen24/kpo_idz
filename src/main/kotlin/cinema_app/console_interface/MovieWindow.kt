package org.example.cinema_app.console_interface

import SelectionWindow
import Window
import cinema_app.dao.CinemaDao
import cinema_app.entity.Movie
import cinema_app.entity.parseTime

class MovieWindow(
    private var cinema: CinemaDao,
    private var cinemaConsoleApplication: CinemaConsoleApplication,
    private var movie: Movie,
    override var parent: Window?
) : Window {
    override fun print() {
        println(
            "\n1.Добавить сеанс\n" + "2.Изменить расписание сеанса\n" + "3.Удалить сеанс\n" + "4.Изменить описание фильма\n" + "5.Удалить фильм"
        )
    }


    override fun stepIn(): Window? {

        val input = readln().trim()

        if (input == "1") {

            println("Введите новое время в формате \"минута час день месяц год\"")

            val time = parseTime()

            if (time == null) {

                println("Сеанс не был добавлен\n")

                return parent
            }

            if (cinema.addSession(movie, time)) {

                println("Сеанс был добавлен\n")

                cinemaConsoleApplication.runtimeReAssemble()

                return cinemaConsoleApplication.mainWindow
            }

        } else if (input == "2") {

            return SelectionWindow("\nВыберите сеанс из списка ниже (введите дату в формате \"минута час день месяц год\"):\n" + cinema.getSession { it.movieName == movie.name }
                .joinToString(separator = "\n", transform = { "${it.movieName} ${it.sessionTime}" }),
                cinema.getSession { it.movieName == movie.name }.associate {
                    it.sessionTime.toString() to ChangeTimeWindow(
                        parent, it, cinema, cinemaConsoleApplication
                    )
                }.toMutableMap(),
                parent
            ).also { parent = cinemaConsoleApplication.mainWindow }

        } else if (input == "3") {

            return SelectionWindow("\nВыберите сеанс, который хотите удалить, из списка ниже (введите дату в формате \"минута час день месяц год\"):\n" + cinema.getSession { it.movieName == movie.name }
                .joinToString(separator = "\n", transform = { "${it.movieName} ${it.sessionTime}" }),
                cinema.getSession { it.movieName == movie.name }.associate {
                    it.sessionTime.toString() to DeleteSessionWindow(
                        parent, cinema, cinemaConsoleApplication, it
                    )
                }.toMutableMap(),
                parent
            ).also { parent = cinemaConsoleApplication.mainWindow }

        } else if (input == "4") {

            println("Введите новое описание")

            val description = readln()

            cinema.changeDescription(movie, description)

        } else if (input == "5") {

            if (cinema.deleteMovie(movie)) {

                println("Успешно удалено")

                cinemaConsoleApplication.runtimeReAssemble()

                return cinemaConsoleApplication.mainWindow

            } else {

                println("Такого фильма нет в базе данных")

            }
        }
        return parent
    }
}
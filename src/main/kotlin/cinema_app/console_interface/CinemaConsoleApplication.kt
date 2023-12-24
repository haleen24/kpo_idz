package org.example.cinema_app.console_interface

import ReFundTicketWindow
import SelectionWindow
import SellTicketWindow
import Window
import cinema_app.console_interface.PlaceDisplayWindow
import cinema_app.dao.CinemaDao
import cinema_app.entity.Place
import cinema_app.entity.Session


fun parsePlace(session: Session): Place? {

    try {

        val (row, seat) = readln().split(' ').map { it.toInt() }

        return session.cinemaHall.findSeat(row, seat)

    } catch (exc: IndexOutOfBoundsException) {

        println("Неправильно введены место и ряд\n")

    } catch (exc: NumberFormatException) {

        println("Неправильно введены место и ряд\n")

    }
    return null
}


class CinemaConsoleApplication(private var cinemaDao: CinemaDao) {

    var mainWindow: Window

    init {
        mainWindow = SelectionWindow("", mutableMapOf(), null)

        runtimeReAssemble()
    }

    fun runtimeReAssemble() {

        mainWindow =
            SelectionWindow(
                "1. Продажа билетов" +
                        "\n2. Возврат билетов" +
                        "\n3. Отображение свободных и проданных мест для конкретного сеанса" +
                        "\n4. Редактирование данных о фильмах или сеансах их показа" +
                        "\n5. Отметка занятых мест",
                mutableMapOf(), null
            )

        val sessionsSellTicketWindow =
            SelectionWindow(
                "\nВыберите сеанс (введите время нужного сеанса) в формате \"минута час день месяц год\":\n" + cinemaDao.getSession()
                    .joinToString(separator = "\n", transform = { "${it.movieName} ${it.sessionTime}" }),
                cinemaDao.getSession()
                    .associate { it.sessionTime.toString() to SellTicketWindow(it, mainWindow, cinemaDao) }
                    .toMutableMap(), mainWindow
            )

        sessionsSellTicketWindow.parent = mainWindow

        (mainWindow as SelectionWindow).mapWindow["1"] = sessionsSellTicketWindow

        val sessionsReFundTicketWindow =
            SelectionWindow(
                "\nВыберите сеанс (введите время нужного сеанса) в формате \"минута час день месяц год\":\n" + cinemaDao.getSession()
                    .joinToString(separator = "\n", transform = { "${it.movieName} ${it.sessionTime}" }),
                cinemaDao.getSession()
                    .associate { it.sessionTime.toString() to ReFundTicketWindow(it, mainWindow, cinemaDao) }
                    .toMutableMap(), mainWindow
            )

        sessionsReFundTicketWindow.parent = mainWindow

        (mainWindow as SelectionWindow).mapWindow["2"] = sessionsReFundTicketWindow

        val placeDisplayWindow = SelectionWindow(
            "\nВыберите сеанс(введите время нужного сеанса) в формате \"минута час день месяц год\":\n" + cinemaDao.getSession()
                .joinToString(separator = "\n", transform = { "${it.movieName} ${it.sessionTime}" }),
            cinemaDao.getSession().associate { it.sessionTime.toString() to PlaceDisplayWindow(it, mainWindow) }
                .toMutableMap(), mainWindow
        )

        placeDisplayWindow.parent = mainWindow

        (mainWindow as SelectionWindow).mapWindow["3"] = placeDisplayWindow

        val movieShowWindow =
            SelectionWindow(
                "\nВведите имя фильма из списка ниже:\n" + cinemaDao.getMovies()
                    .joinToString(separator = "\n", transform = { it.name }),
                cinemaDao.getMovies().associate { it.name to MovieWindow(cinemaDao, this, it, mainWindow) }
                    .toMutableMap(), mainWindow
            )

        val movieChangeWindow = SelectionWindow(
            "\n1. Изменить существующий фильм или сеанс\n" +
                    "2. Добавить фильм",
            mutableMapOf("1" to movieShowWindow, "2" to MovieAddWindow(cinemaDao, this, mainWindow)), mainWindow
        )

        movieChangeWindow.parent = mainWindow

        (mainWindow as SelectionWindow).mapWindow["4"] = movieChangeWindow

        val markPlaceWindow = SelectionWindow(
            "\nВыберите сеанс (введите время нужного сеанса) в формате \"минута час день месяц год\":\n" + cinemaDao.getSession()
                .joinToString(separator = "\n", transform = { "${it.movieName} ${it.sessionTime}" }),
            cinemaDao.getSession()
                .associate { it.sessionTime.toString() to PlaceMarkWindow(it, cinemaDao, this, mainWindow) }
                .toMutableMap(), mainWindow
        )

        markPlaceWindow.parent = mainWindow

        (mainWindow as SelectionWindow).mapWindow["5"] = markPlaceWindow
    }

    fun start() {

        var currentWindow: Window? = mainWindow

        while (currentWindow != null) {

            currentWindow.print()

            currentWindow = currentWindow.stepIn()

        }
        println("Выход из приложения...")
    }
}
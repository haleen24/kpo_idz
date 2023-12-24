@file:Suppress("PackageDirectoryMismatch")

package org.example.cinema.application.console

import SelectionWindow
import SellTicketWindow
import Window
import cinema.application.console.AuthorizationWindow
import org.example.cinema.application.dao.CinemaDao
import org.example.cinema.application.entity.Place
import org.example.cinema.application.entity.Session
import cinema.application.console.MovieAddWindow


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

    // Сборка меню
    fun runtimeReAssemble() {

        // основное окно
        mainWindow =
            SelectionWindow(
                "1. Продажа билетов" +
                        "\n2. Возврат билетов" +
                        "\n3. Отображение свободных и проданных мест для конкретного сеанса" +
                        "\n4. Редактирование данных о фильмах или сеансах их показа" +
                        "\n5. Отметка занятых мест",
                mutableMapOf(), null
            )

        // окно продажи билетов
        val sessionsSellTicketWindow =
            SelectionWindow(
                "\nВыберите сеанс (введите время нужного сеанса) в формате \"минута час день месяц год\":\n" + cinemaDao.getSession()
                    .joinToString(separator = "\n", transform = { "${it.movieName} ${it.sessionTime}" }),
                cinemaDao.getSession()
                    .associate { it.sessionTime.toString() to SellTicketWindow(it, mainWindow, cinemaDao) }
                    .toMutableMap(), mainWindow
            )
        // сохранение основного окна как точки, в которую можно выйти из окна продажи билетов
        sessionsSellTicketWindow.parent = mainWindow

        (mainWindow as SelectionWindow).mapWindow["1"] = sessionsSellTicketWindow

        // окно возвратов
        val sessionsRefundTicketWindow =
            SelectionWindow(
                "\nВыберите сеанс (введите время нужного сеанса) в формате \"минута час день месяц год\":\n" + cinemaDao.getSession()
                    .joinToString(separator = "\n", transform = { "${it.movieName} ${it.sessionTime}" }),
                cinemaDao.getSession()
                    .associate { it.sessionTime.toString() to RefundTicketWindow(it, mainWindow, cinemaDao) }
                    .toMutableMap(), mainWindow
            )

        sessionsRefundTicketWindow.parent = mainWindow

        (mainWindow as SelectionWindow).mapWindow["2"] = sessionsRefundTicketWindow

        // окно для выбора отображения свободных/занятых мест
        val placeDisplayWindow = SelectionWindow(
            "\nВыберите сеанс(введите время нужного сеанса) в формате \"минута час день месяц год\":\n" + cinemaDao.getSession()
                .joinToString(separator = "\n", transform = { "${it.movieName} ${it.sessionTime}" }),
            cinemaDao.getSession().associate { it.sessionTime.toString() to PlaceDisplayWindow(it, mainWindow) }
                .toMutableMap(), mainWindow
        )

        placeDisplayWindow.parent = mainWindow

        (mainWindow as SelectionWindow).mapWindow["3"] = placeDisplayWindow

        // окно просмотра фильмов
        val movieShowWindow =
            SelectionWindow(
                "\nВведите имя фильма из списка ниже:\n" + cinemaDao.getMovies()
                    .joinToString(separator = "\n", transform = { it.name }),
                cinemaDao.getMovies().associate { it.name to MovieWindow(cinemaDao, this, it, mainWindow) }
                    .toMutableMap(), mainWindow
            )

        // окно первичного выбора редактирования
        val movieChangeWindow = SelectionWindow(
            "\n1. Изменить существующий фильм или сеанс\n" +
                    "2. Добавить фильм",
            mutableMapOf("1" to movieShowWindow, "2" to MovieAddWindow(cinemaDao, this, mainWindow)), mainWindow
        )

        movieChangeWindow.parent = mainWindow

        (mainWindow as SelectionWindow).mapWindow["4"] = movieChangeWindow

        // окно для отметки посещения сеансов
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

    fun start(accountFilePath: String) {

        // окно выбора авторизации/регистрации
        var currentWindow: Window? = SelectionWindow(
            "1.Авторизация\n2.Регистрация",
            mutableMapOf(
                "1" to AuthorizationWindow(null, mainWindow, accountFilePath),
                "2" to RegistrationWindow(null, mainWindow, accountFilePath)
            ),
            null
        )

        (currentWindow as SelectionWindow).mapWindow["1"]!!.parent = currentWindow

        currentWindow.mapWindow["2"]!!.parent = currentWindow


        while (currentWindow != null) {

            currentWindow.print()

            currentWindow = currentWindow.stepIn()

        }
        println("Выход из приложения...")
    }
}
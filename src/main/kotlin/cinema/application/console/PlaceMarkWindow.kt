package org.example.cinema.application.console

import Window
import org.example.cinema.application.dao.CinemaDao
import org.example.cinema.application.entity.Session

class PlaceMarkWindow(
    val session: Session,
    private val cinemaDao: CinemaDao,
    private val cinemaConsoleApplication: CinemaConsoleApplication,
    override var parent: Window?
) : Window {
    override fun print() {

        println(
            "Для выхода введите любую последовательность символов, не представляющую из себя двух чисел через пробел.\n" +
                    "В водите по 2 числа - номер ряда и номер кресла в одной строке человека, который пришел на сеанс"
        )

    }

    override fun stepIn(): Window {

        while (true) {

            val place = parsePlace(session) ?: break

            if (!cinemaDao.takePlace(session, place)) {

                println("Невозможно занять это место")

                continue
            }
        }
        cinemaConsoleApplication.runtimeReAssemble()

        return cinemaConsoleApplication.mainWindow
    }
}
package org.example.cinema.application.console

import Window
import org.example.cinema.application.dao.CinemaDao
import org.example.cinema.application.entity.Session

class DeleteSessionWindow(
    override var parent: Window?,
    private val cinemaDao: CinemaDao,
    private val cinemaConsoleApplication: CinemaConsoleApplication,
    val session: Session
) : Window {
    override fun print() {
    }

    override fun stepIn(): Window? {

        if (cinemaDao.deleteSession(session)) {

            println("Сеанс удален\n")

            cinemaConsoleApplication.runtimeReAssemble()

            return cinemaConsoleApplication.mainWindow
        }

        println("Ошибка удаления сеанса\n")

        return parent

    }

}
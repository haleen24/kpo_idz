package org.example.cinema.application.console

import Window
import org.example.cinema.application.dao.CinemaDao
import org.example.cinema.application.entity.Session
import org.example.cinema.application.entity.Time.Companion.parseTimeFromConsole

class ChangeTimeWindow(

    override var parent: Window?,

    val session: Session,

    private val cinemaDao: CinemaDao,

    private val consoleApplication: CinemaConsoleApplication

) : Window {

    override fun print() {

        println("Введите время в формате \"минута час день месяц год\"")

    }

    override fun stepIn(): Window? {

        val time = parseTimeFromConsole() ?: return parent

        cinemaDao.changeTime(session, time)

        println("Время успешно изменено\n")

        consoleApplication.runtimeReAssemble()

        return consoleApplication.mainWindow
    }
}
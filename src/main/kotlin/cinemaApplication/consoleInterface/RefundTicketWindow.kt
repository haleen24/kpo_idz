package cinemaApplication.consoleInterface

import Window
import cinemaApplication.dao.CinemaDao
import cinemaApplication.entity.Session


class RefundTicketWindow(var session: Session, override var parent: Window?, private var cinemaDao: CinemaDao) :
    Window {
    override fun print() {

        println("Введите место: ряд и номер через пробел")

    }

    override fun stepIn(): Window? {

        val place = parsePlace(session)

        if (place == null) {

            println("Такого места не существует\n")

        } else if (cinemaDao.refundTicket(session, place)) {

            println("Успешно\n")

        } else {

            println("Не возможно вернуть билет\n")

        }
        return parent
    }

}
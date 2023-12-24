import cinema_app.dao.CinemaDao
import cinema_app.entity.Session
import org.example.cinema_app.console_interface.parsePlace


class ReFundTicketWindow(var session: Session, override var parent: Window?, private var cinemaDao: CinemaDao) :
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
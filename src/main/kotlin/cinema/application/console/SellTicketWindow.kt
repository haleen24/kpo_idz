
import org.example.cinema.application.console.parsePlace
import org.example.cinema.application.dao.CinemaDao
import org.example.cinema.application.entity.Session

class SellTicketWindow(private var session: Session, override var parent: Window?, private var cinemaDao: CinemaDao) : Window {

    override fun print() {

        println("Введите место: ряд и номер через пробел")

    }

    override fun stepIn(): Window? {

        val place = parsePlace(session)

        if (place == null) {

            println("Такого места не существует\n")

        } else if (cinemaDao.sellTicket(session, place)) {

            println("Успешно\n")

        } else {

            println("Невозможно продать билет")

        }
        return parent
    }

}
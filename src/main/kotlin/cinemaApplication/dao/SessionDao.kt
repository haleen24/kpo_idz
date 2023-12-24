package cinemaApplication.dao

import cinemaApplication.entity.Place
import cinemaApplication.entity.Session
import cinemaApplication.entity.Time

// интерфейс объекта, который отвечает за сессии
interface SessionDao {

    fun getSessions(): List<Session>

    fun addSession(session: Session): Boolean

    fun getSessionByTime(time: Time): Session?

    fun deleteSession(session: Session): Boolean

    fun deleteSession(filter: (session: Session) -> Boolean): Boolean

    fun buyTicket(session: Session, place: Place): Boolean

    fun refundTicket(session: Session, place: Place): Boolean

    fun changeTime(session: Session, time: Time)

    fun takePlace(session: Session, place: Place): Boolean

}
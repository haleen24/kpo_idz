package cinema_app.dao

import cinema_app.entity.Place
import cinema_app.entity.Session
import cinema_app.entity.Time

interface SessionDao {

    fun getSessions(): List<Session>

    fun addSession(session: Session): Boolean

    fun getSessionByTime(time: Time): Session?

    fun deleteSession(session: Session): Boolean

    fun deleteSession(filter: (session: Session) -> Boolean): Boolean

    fun buyTicket(session: Session, place: Place): Boolean

    fun refundTicket(session: Session, place: Place): Boolean

    fun changeTime(session: Session, time: Time): Unit

    fun takePlace(session: Session, place: Place): Boolean

}
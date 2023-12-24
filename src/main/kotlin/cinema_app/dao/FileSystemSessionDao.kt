package cinema_app.dao

import cinema_app.entity.Place
import cinema_app.entity.Session
import cinema_app.entity.Time
import cinema_app.entity.Time.Companion.getTimeRightNow
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

@Serializable
class FileSystemSessionDao(private var filePath: String) : SessionDao {

    private var sessions: MutableSet<Session>

    init {

        val file = File(filePath)

        if (!file.exists()) {

            file.createNewFile()

        }

        sessions = try {

            Json.decodeFromString(file.readLines().joinToString(separator = ""))

        } catch (serializeException: SerializationException) {

            mutableSetOf()

        } catch (serializeException: IllegalArgumentException) {

            mutableSetOf()

        }
    }


    override fun getSessions(): List<Session> = sessions.toList()

    override fun addSession(session: Session): Boolean {

        if (sessions.any { it.sessionTime == session.sessionTime }) {

            return false

        }
        return sessions.add(session).also { saveSessions() }
    }

    override fun getSessionByTime(time: Time): Session? = sessions.find { it.sessionTime == time }

    override fun deleteSession(session: Session) = sessions.remove(session).also { saveSessions() }

    override fun deleteSession(filter: (session: Session) -> Boolean): Boolean =
        sessions.removeAll(filter).also { saveSessions() }

    override fun buyTicket(session: Session, place: Place): Boolean {
        return if (place.isBought || session.sessionTime < getTimeRightNow()) false else {

            place.isBought = true

            true.also { saveSessions() }
        }
    }

    override fun refundTicket(session: Session, place: Place): Boolean {

        return if (!place.isBought || session.sessionTime < getTimeRightNow()) false else {

            place.isBought = false

            true.also { saveSessions() }
        }
    }

    override fun changeTime(session: Session, time: Time) {

        session.sessionTime = time

        saveSessions()
    }

    override fun takePlace(session: Session, place: Place): Boolean {

        if (!place.isBought || place.isOccupied) {

            return false

        }

        place.isOccupied = true

        saveSessions()

        return true
    }

    // Чтобы в случае чего, можно было обновлять извне
    @Suppress("MemberVisibilityCanBePrivate")
    fun saveSessions() = File(filePath).writeText(Json.encodeToString(sessions))

}
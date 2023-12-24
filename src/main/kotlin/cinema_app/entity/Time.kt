package cinema_app.entity

import kotlinx.serialization.Serializable
import java.time.DateTimeException
import java.time.LocalDateTime


@Suppress("EqualsOrHashCode")
@Serializable
class Time(var year: Int, var month: Int, var day: Int, var hour: Int, var minute: Int) {

    companion object {
        fun getTimeRightNow(): Time {

            val time = LocalDateTime.now()

            return Time(time.year, time.monthValue, time.dayOfMonth, time.hour, time.minute)
        }

        @Suppress("MemberVisibilityCanBePrivate")
        fun getTime(year: Int, month: Int, day: Int, hour: Int, minute: Int): Time? {

            try {

                LocalDateTime.of(year, month, day, hour, minute)

                return Time(year, month, day, hour, minute)

            } catch (exc: DateTimeException) {

                return null

            }
        }

        fun parseTimeFromConsole(): Time? {
            try {

                val (minute, hour, day, month, year) = readln().trim().split(' ').map { it.toInt() }

                return getTime(year, month, day, hour, minute)

            } catch (exc: IndexOutOfBoundsException) {

                println("Неправильно введено времяд\n")

            } catch (exc: NumberFormatException) {

                println("Неправильно введено время\n")

            }
            return null
        }
    }

    override operator fun equals(other: Any?): Boolean {

        if (other == null || other !is Time) {

            return false

        }

        return year == other.year && month == other.month && day == other.day && hour == other.hour && minute == other.minute
    }

    override fun toString(): String = "$minute $hour $day $month $year"

    operator fun compareTo(other: Time): Int {
        val thisList = listOf(year, month, day, hour, minute)
        val otherList = listOf(other.year, other.month, other.day, other.hour, other.minute)
        for (i in 0..<5) {
            if (thisList[i] > otherList[i]) {
                return 1
            } else if (thisList[i] < otherList[i]) {
                return -1
            }
        }
        return 0
    }


}
package org.example.cinema.application.console

import Window
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.example.cinema.application.entity.Session
import java.io.File
import java.io.IOException

class PlaceDisplayWindow(private val session: Session, override var parent: Window?) : Window {

    override fun print() {

        println("Введите имя файла (без расширения!), в который отобразится информация о местах")

    }

    override fun stepIn(): Window? {

        val file = File("${readln().trim()}.json")

        if (!file.exists()) {

            try {

                file.createNewFile()

            } catch (exc: IOException) {

                println("Не удалось создать файл\n")

                return parent
            }
        }

        file.writeText(Json.encodeToString(session.cinemaHall.getAllSeats())).also { println("Информация записана\n") }

        return parent
    }
}
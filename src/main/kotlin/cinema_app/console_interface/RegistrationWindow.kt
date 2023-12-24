package org.example.cinema_app.console_interface

import Window
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.security.MessageDigest
import java.util.*

class RegistrationWindow(override var parent: Window?, var mainWindow: Window, val filePath: String) : Window {
    override fun print() {

        println("Введите логи и пароль через enter")

    }

    override fun stepIn(): Window? {

        val file = File(filePath)

        if (!file.exists()) {

            file.createNewFile()

        }

        val login = readln()
        val password = readln()

        val accounts = try {
            Json.decodeFromString<MutableSet<Account>>(
                file.readLines().joinToString(separator = "", transform = { it })
            )

        } catch (serializeException: SerializationException) {

            mutableSetOf()

        } catch (serializeException: IllegalArgumentException) {

            mutableSetOf()

        }

        if (accounts.find { it.login == login } != null) {

            println("Логин занят")

            return parent
        }

        val md = MessageDigest.getInstance("SHA-256")

        val input = password.toByteArray()

        val bytes = md.digest(input)

        val result = Base64.getEncoder().encodeToString(bytes)

        accounts.add(Account(login, result))

        file.writeText(Json.encodeToString(accounts))
        return mainWindow
    }
}
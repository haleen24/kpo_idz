package org.example.cinemaApp.console_interface

import Window
import cinemaApplication.consoleInterface.Account
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.File
import java.security.MessageDigest
import java.util.*

class AuthorizationWindow(override var parent: Window?, private var mainWindow: Window, private val filePath: String) : Window {
    override fun print() {

        println("Введите логин и пароль через enter")

    }

    override fun stepIn(): Window? {

        val file = File(filePath)

        if (!file.exists()) {

            file.createNewFile()
        }

        val accounts = try {
            Json.decodeFromString<MutableSet<Account>>(
                file.readLines().joinToString(separator = "", transform = { it })
            )

        } catch (serializeException: SerializationException) {

            mutableSetOf()

        } catch (serializeException: IllegalArgumentException) {

            mutableSetOf()

        }

        val login = readln()

        val password = readln()

        val account = accounts.find { it.login == login }

        if (account == null) {

            println("Такого аккаунта не существует")

            return parent
        }

        // шифровка полученного от пользователя пароля
        val md = MessageDigest.getInstance("SHA-256")

        val input = password.toByteArray()

        val bytes = md.digest(input)

        val result = Base64.getEncoder().encodeToString(bytes)

        // сравнение полученного и того, что имеется
        return if (result == account.password) mainWindow.also { println("Успешно") } else parent.also { println("Неверный пароль") }

    }
}
package cinema.application.console

import Window
import org.example.cinema.application.console.CinemaConsoleApplication
import org.example.cinema.application.dao.CinemaDao
import org.example.cinema.application.entity.Movie

class MovieAddWindow(
    private var cinemaDao: CinemaDao,
    private var cinemaConsoleApplication: CinemaConsoleApplication,
    override var parent: Window?
) : Window {
    override fun print() {
        println("Введите название фильма и его описание через enter")
    }

    override fun stepIn(): Window? {

        val name = readln().trim()

        val description = readln().trim()

        val movie = Movie(name, description)

        if (cinemaDao.addMovie(movie)) {

            println("Фильм добавлен\n")

            cinemaConsoleApplication.runtimeReAssemble()

            return cinemaConsoleApplication.mainWindow
        }

        println("Ошибка добавления: такой фильм с таким описанием уже был добавлен")

        return parent
    }
}
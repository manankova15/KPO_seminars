package System

import blueText
import checkPassword
import errorText
import hashPassword
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

val filmRepository = DataRepository(Film::class.java, "data")
val sessionRepository = DataRepository(Session::class.java, "data")
val ticketRepository = DataRepository(Ticket::class.java, "data")
val userRepository = DataRepository(User::class.java, "data")

val maxRow = 4
val maxNumber = 6

class CinemaManager() {
    // Метод для фиксации продажи билета посетителю на выбранный сеанс с выбором места
    fun sellTicket(sessionId: Int, seatRow: Int, seatPlace: Int): Boolean {
        val session = sessionRepository.getAll().find { it.id == sessionId}
        val seat = session?.seats?.find { it.row == seatRow && it.place == seatPlace }

        if (session != null && seat != null && !seat.isOccupied) {
            val ticket = Ticket(
                session = session,
                seat = seat,
                price = calculateTicketPrice(seat.row)
            )
            ticketRepository.add(ticket)
            seat.isOccupied = true
            sessionRepository.saveData()
            return true
        }
        return false
    }

    // Метод для возврата билета до начала сеанса
    fun returnTicket(ticketId: Int): Boolean {
        val ticket = ticketRepository.getAll().find { it.id == ticketId }
        val session = (ticket as Ticket)?.session

        // Возврат возможен только до начала сеанса
        if (ticket != null && session != null && isBeforeSessionStart(session.startTime)) {
            ticket.seat.isOccupied = false
            ticketRepository.remove(ticket.id)
            sessionRepository.saveData()
            return true
        }
        return false
    }

    // Метод для отображения свободных и проданных мест для выбранного сеанса
    fun displaySeats(sessionId: Int) {
        val session = sessionRepository.getAll().find { it.id == sessionId }

        if (session == null) {
            println(errorText("Сеанс не найден"))
        } else {session.printSeats()}
    }

    // Метод для редактирования данных о фильмах и расписания сеансов
    fun editFilmAndSchedule(filmId: Int, newTitle: String, newDuration: Int) {
        val film = filmRepository.getAll().find { it.id == filmId }

        if (film != null) {
            film.title = newTitle
            film.durationMinutes = newDuration

            filmRepository.saveData()
        } else {
            println("Film not found.")
        }
    }

    fun editSession(sessionId: Int, newTime: String) {
        val session = sessionRepository.getAll().find { it.id == sessionId }

        if (session != null) {
            session.startTime = newTime

            filmRepository.saveData()
        } else {
            println("Session not found.")
        }
    }

    // Метод для регистрации нового пользователя
    fun registerUser(login: String, password: String)  {
        if (userRepository.getAll().find { it.login == login} == null) {
            val hashedPassword = hashPassword(password)
            val newUser = User(login, hashedPassword)
            userRepository.add(newUser)
            userRepository.saveData()
            println(blueText("Регистрация прошла успешно"))
        } else {
            println(errorText("Данный пользователь уже существует"))
        }
    }

    // Метод для авторизации пользователя
    fun authenticateUser(login: String, password: String): Boolean{
        val user = userRepository.getAll().find { it.login == login }
        if (user!=null && checkPassword(password, user.hashedPassword)) {
            println(blueText("Авторизация прошла успешно"))
            return true
        } else {
            println(errorText("Неверный логин или пароль"))
            return false
        }
    }

    fun markOccupiedSeats(sessionId: Int, seatIds: List<Int>) {
        val session = sessionRepository.getAll().find { it.id == sessionId }

        if (session != null) {
            val seats = session.seats.filter { seatIds.contains(it.id) }
            seats.forEach { it.isOccupied = true }

            // Сохранение изменений в репозитории сеансов
            sessionRepository.saveData()

            // Создание новых билетов и добавление их в репозиторий билетов
            for (seat in seats) {
                val newTicket = Ticket(session, seat, calculateTicketPrice(seat.row))
                ticketRepository.add(newTicket)
            }
            ticketRepository.saveData()
        } else {
            println("Session not found.")
        }
    }

    // Метод для расчета цены билета
    private fun calculateTicketPrice(row: Int): Int {
        return 300 * (maxRow - row)
    }

    // Проверка, что текущее время раньше начала сеанса
    private fun isBeforeSessionStart(sessionStartTime: String): Boolean {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
        val startTime = LocalDateTime.parse(sessionStartTime, formatter)
        return LocalDateTime.now().isBefore(startTime)
    }
}

// Проверка на корректность введенных данных о времени
fun isValidDateTimeFormat(dateTimeString: String): Boolean {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")

    return try {
        // Пытаемся распарсить строку в LocalDateTime с использованием заданного формата
        LocalDateTime.parse(dateTimeString, formatter)
        true // Если успешно, строка соответствует формату
    } catch (e: DateTimeParseException) {
        false // В противном случае, строка не соответствует формату
    }
}
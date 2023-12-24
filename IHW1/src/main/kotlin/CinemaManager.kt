package System

import java.time.LocalDateTime

class CinemaManager(private val dataRepository: DataRepository<Film>, private val sessionRepository: DataRepository<Session>) {
    // Метод для фиксации продажи билета посетителю на выбранный сеанс с выбором места
    fun sellTicket(sessionId: Int, seatId: Int): Boolean {
        val session = sessionRepository.getAll().find { it.id == sessionId }
        val seat = session?.seats?.find { it.id == seatId }

        if (session != null && seat != null && !seat.isOccupied) {
            val ticket = Ticket(
                id = dataRepository.getAll().size + 1,
                session = session,
                seat = seat,
                price = calculateTicketPrice(session)
            )
            dataRepository.add(ticket)
            seat.isOccupied = true
            sessionRepository.saveData()
            return true
        }
        return false
    }

    // Метод для возврата билета до начала сеанса
    fun returnTicket(ticketId: Int): Boolean {
        val ticket = dataRepository.getAll().find { it.id == ticketId }
        val session = ticket?.session

        if (ticket != null && session != null && isBeforeSessionStart(session.startTime)) {
            ticket.seat.isOccupied = false
            dataRepository.getAll().remove(ticket)
            sessionRepository.saveData()
            return true
        }
        return false
    }

    // Метод для отображения свободных и проданных мест для выбранного сеанса
    fun displaySeats(sessionId: Int) {
        val session = sessionRepository.getAll().find { it.id == sessionId }

        if (session != null) {
            val occupiedSeats = session.seats.filter { it.isOccupied }
            val freeSeats = session.seats - occupiedSeats

            println("Occupied seats: $occupiedSeats")
            println("Free seats: $freeSeats")
        } else {
            println("Session not found.")
        }
    }

    // Метод для редактирования данных о фильмах и расписания сеансов
    fun editFilmAndSchedule(filmId: Int, newTitle: String, newDuration: Int) {
        val film = dataRepository.getAll().find { it.id == filmId }

        if (film != null) {
            film.title = newTitle
            film.durationMinutes = newDuration

            dataRepository.saveData()
        } else {
            println("Film not found.")
        }
    }

    // Метод для отметки занятых мест в зале посетителями конкретного сеанса
    fun markOccupiedSeats(sessionId: Int, seatIds: List<Int>) {
        val session = sessionRepository.getAll().find { it.id == sessionId }

        if (session != null) {
            val seats = session.seats.filter { seatIds.contains(it.id) }
            seats.forEach { it.isOccupied = true }
            sessionRepository.saveData()
        } else {
            println("Session not found.")
        }
    }

    // Метод для расчета цены билета
    private fun calculateTicketPrice(session: Session): Double {
        return 10.0
    }

    // Проверка, что текущее время раньше начала сеанса
    private fun isBeforeSessionStart(sessionStartTime: LocalDateTime): Boolean {
        return LocalDateTime.now().isBefore(sessionStartTime)
    }
}
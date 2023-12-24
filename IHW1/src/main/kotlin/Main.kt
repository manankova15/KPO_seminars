package System
import blueText
import customFormat
import errorText
import java.time.LocalDateTime

fun main() {
    val cinemaManager = CinemaManager()

    while (true) {
        try{
        println("1. Купить билет")
        println("2. Вернуть билет")
        println("3. Отобразить места в зале")
        println("4. Редактировать/добавить фильм или сеанс")
        println("5. Выбрать несколько мест для покупки")
        println("6. Авторизироваться")
        println("7. Зарегистрироваться")
        println("8. Выйти")

        println(blueText(customFormat("Выберите команду")))
        val input = readLine()?.toIntOrNull()

        when (input) {
            1 -> {
                val sessions = sessionRepository.getAll()
                for (session in sessions) {
                    println("Id сеанса: ${session.id}, Film: ${session.film.getFilmName()}, Start time: ${session.startTime}")
                }
                if (sessions.size == 0) {
                    println(errorText("Нет сеансов"))
                    continue
                }

                print("Введите ID сеанса, на который хотите приобрести билет: ")
                val sessionId = readLine()?.toInt()
                if (sessionId != null) {

                    cinemaManager.displaySeats(sessionId)
                    print("Введите ряд(1-$maxRow) и место(1-$maxNumber) через пробел: ")
                    readLine()?.split(" ")?.map { it.toInt() }?.let { (row, place) ->
                        val success = cinemaManager.sellTicket(sessionId, row, place)
                        if (success) {
                            println(blueText("Билет с Id ${ticketRepository.getAll().last().id} успешно продан"))
                        } else {
                            println(errorText("Ошибка при продаже билета"))
                        }
                    } ?: run {
                        println(errorText("Ошибка: введенные значения для ряда или места не являются числами"))
                    }
                } else {
                    println(errorText("Ошибка в ID"))
                }
            }

            2 -> {
                print("Введите ID билета для возврата: ")
                val ticketId = readLine()?.toInt()

                if (ticketId != null) {
                    val success = cinemaManager.returnTicket(ticketId)
                    if (success) {
                        println(blueText("Билет успешно возвращен"))
                    } else {
                        println(errorText("Ошибка при возврате билета"))
                    }
                } else {
                    println(errorText("Ошибка в ID билета"))
                }
            }

            3 -> {
                val sessions = sessionRepository.getAll()
                for (session in sessions) {
                    println("Id сеанса: ${session.id}, Film: ${session.film.getFilmName()}, Start time: ${session.startTime}")
                }
                print("Введите ID сеанса, для которого хотите посмотреть места в зале: ")
                val sessionId = readLine()?.toInt()

                if (sessionId != null) {
                    cinemaManager.displaySeats(sessionId)
                } else {
                    println(errorText("Ошибка в ID"))
                }
            }

            4 -> {
                println("Выберите действие:")
                println("1. Редактировать существующий сеанс")
                println("2. Редактировать существующий фильм")
                println("3. Добавить новый сеанс")
                println("4. Добавить новый фильм")

                val subAction = readLine()?.toIntOrNull()

                when (subAction) {
                    1 -> {
                        val sessions = sessionRepository.getAll()
                        for (session in sessions) {
                            println("Id сеанса: ${session.id}, Film: ${session.film.getFilmName()}, Start time: ${session.startTime}")
                        }
                        print("Введите ID сеанса для редактирования: ")
                        val sessionId = readLine()?.toInt()
                        if (sessionId != null) {
                            print("Введите новое время сеанса в формате \"2023-12-31T23:59\": ")
                            val newTime: String = readLine() ?: "ErrorTitle"
                            if (isValidDateTimeFormat(newTime)) {
                                cinemaManager.editSession(sessionId, newTime)
                                println(blueText("Данные успешно отредактированы"))
                            } else {
                                println(errorText("Время не соответствует требуемому формату"))
                            }
                        } else {
                            println(errorText("Ошибка в ID сеанса"))
                        }
                    }

                    2 -> {
                        val films = filmRepository.getAll()
                        for (film in films) {
                            println("${film}")
                        }
                        print("Введите ID фильма для редактирования: ")
                        val filmId = readLine()?.toInt()
                        if (filmId != null) {
                            print("Введите новый заголовок фильма: ")
                            val newTitle: String = readLine() ?: "ErrorTitle"
                            print("Введите новую длительность фильма: ")
                            val newDuration = readLine()?.toInt()
                            if (newDuration != null) {
                                cinemaManager.editFilmAndSchedule(filmId, newTitle, newDuration)
                                println(blueText("Данные успешно отредактированы"))
                            } else {
                                println(errorText("Длительность фильма должна быть представлена в виде числа"))
                            }
                        } else {
                            println(errorText("Ошибка в ID фильма"))
                        }
                    }

                    3 -> {
                        val films = filmRepository.getAll()
                        for (film in films) {
                            println("${film}")
                        }
                        print("Введите Id фильма, для которого создается новый сеанс: ")
                        val filmId = readLine()?.toInt()
                        if (filmId != null) {
                            val film = filmRepository.getAll().find { it.id == filmId }
                            if (film == null) {
                                println(errorText("Ошибка в ID фильма"))
                                continue
                            }
                            print("Введите новое время начала сеанса в формате \"2023-12-31T23:59\": ")
                            val newTime: String = readLine() ?: "ErrorTitle"
                            if (isValidDateTimeFormat(newTime)) {
                                sessionRepository.add(Session(film, newTime))
                                println(blueText("Данные успешно отредактированы"))
                            } else {
                                println(errorText("Время не соответствует требуемому формату"))
                            }
                        } else {
                            println(errorText("Ошибка в ID фильма"))
                        }
                    }

                    4 -> {
                        print("Введите название нового фильма: ")
                        val filmTitle = readLine() ?: continue
                        print("Введите длительность фильма в минутах: ")
                        val filmDuration = readLine()?.toInt()

                        if (filmDuration != null && filmDuration > 0) {
                            filmRepository.add(Film(filmTitle, filmDuration))
                        } else {
                            println(errorText("Ошибка в длительности фильма"))
                        }
                    }

                    else -> {
                        println("Неверная подкоманда. Введите число от 1 до 4.")
                    }
                }
            }

            5 -> {
                val seatIds: MutableList<Int> = mutableListOf()
                val sessions = sessionRepository.getAll()
                for (session in sessions) {
                    println("Id сеанса: ${session.id}, Film: ${session.film.getFilmName()}, Start time: ${session.startTime}")
                }

                print("Введите ID сеанса, на который хотите приобрести билеты: ")
                val sessionId = readLine()?.toInt()
                if (sessionId != null) {
                    cinemaManager.displaySeats(sessionId)
                    print("Введите количество мест для покупки: ")
                    val n = readLine()?.toInt()
                    if (n == null || n < 1) {
                        println(errorText("Введенное количество не является положительным числом"))
                        continue
                    }
                    println("Введите место для покупки. Ряд(1-$maxRow) и номер сидения(1-$maxNumber) через пробел")
                    for (i in 0 until n) {
                        print("Введите ${i + 1} место для покупки (ряд и номер сидения через пробел): ")
                        val (row, place) = readLine()?.split(" ") ?: continue
                        if (row?.toIntOrNull() == null || place.toIntOrNull() == null || (row as Int) > maxRow ||
                            (row as Int) < 1 || (place as Int) > maxNumber || (place as Int) < 1
                        ) {
                            println(errorText("Неверный ряд или номер места"))
                            continue
                        }
                        seatIds.add(Seat(row, place).id)
                    }
                    cinemaManager.markOccupiedSeats(sessionId, seatIds)
                    println(blueText("Места с Id: ${seatIds.joinToString()},успешно куплены"))
                } else {
                    println(errorText("Ошибка в ID"))
                }
            }

            6 -> {
                println("Введите логин и пароль (через пробел): ")
                val (login, password) = readLine()?.split(" ") ?: continue
                cinemaManager.authenticateUser(login, password)
            }

            7 -> {
                println("Введите логин и пароль (через пробел): ")
                val (login, password) = readLine()?.split(" ") ?: continue
                cinemaManager.registerUser(login, password)
            }

            8 -> {
                println("Программа завершена.")
                return
            }

            else -> {
                println("Неверная команда. Пожалуйста, выберите число от 1 до 8")
            }
        }
    } catch (e: Exception) {
            println("Произошла неизвестная ошибка: ${e.message}")
        }
    }
}
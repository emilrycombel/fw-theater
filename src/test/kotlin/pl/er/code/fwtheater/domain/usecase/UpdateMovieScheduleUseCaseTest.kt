package pl.er.code.fwtheater.domain.usecase

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import pl.er.code.fwtheater.domain.exception.BusinessRuleException
import pl.er.code.fwtheater.domain.exception.InvalidStateException
import pl.er.code.fwtheater.domain.model.Auditorium
import pl.er.code.fwtheater.domain.model.Movie
import pl.er.code.fwtheater.domain.model.MovieSchedule
import pl.er.code.fwtheater.domain.model.Theater
import java.math.BigDecimal
import java.time.OffsetDateTime

class UpdateMovieScheduleUseCaseTest {

    private val updateMovieScheduleUseCase = UpdateMovieScheduleUseCase()

    @Test
    fun `updateSchedule should return updated MovieSchedule with correct properties`() {
        val theater = object : Theater {
            override var id: String? = "theaterId"
            override var name: String? = "Grand Theater"
        }
        val auditorium = object : Auditorium {
            override var id: String? = "auditoriumId"
            override var capacity: Short? = 200
            override var name: String? = "Main Hall"
            override var theater: Theater? = theater
        }
        val movie = object : Movie {
            override var id: String? = "movieId"
            override var title: String? = "movieTitle"
        }
        val existingSchedule = object : MovieSchedule {
            override var id: String? = "scheduleId"
            override var screenTime: OffsetDateTime? = OffsetDateTime.now().plusDays(1)
            override var movie: Movie? = movie
            override var auditorium: Auditorium? = auditorium
            override var price: BigDecimal = BigDecimal.TEN
        }
        val newStartTime = OffsetDateTime.now().plusDays(2)
        val newPrice = BigDecimal.valueOf(20)
        val movieLength = 120

        val updatedSchedule = updateMovieScheduleUseCase.updateSchedule(
            existingSchedule, newStartTime, newPrice, movieLength, emptyList()
        )

        assertEquals(newStartTime, updatedSchedule.screenTime)
        assertEquals(newPrice, updatedSchedule.price)
        assertEquals(auditorium, updatedSchedule.auditorium)
        assertEquals(movie, updatedSchedule.movie)
    }

    @Test
    fun `updateSchedule should throw InvalidStateException for start time in the past`() {
        val existingSchedule = object : MovieSchedule {
            override var id: String? = "scheduleId"
            override var screenTime: OffsetDateTime? = OffsetDateTime.now().plusDays(1)
            override var movie: Movie? = null
            override var auditorium: Auditorium? = null
            override var price: BigDecimal = BigDecimal.ZERO
        }
        val startTime = OffsetDateTime.now().minusDays(1)
        val price = BigDecimal.TEN
        val movieLength = 120

        assertThrows(InvalidStateException::class.java) {
            updateMovieScheduleUseCase.updateSchedule(
                existingSchedule, startTime, price, movieLength, emptyList()
            )
        }
    }

    @Test
    fun `updateSchedule should throw BusinessRuleException for overlapping schedules`() {
        val existingSchedule = object : MovieSchedule {
            override var id: String? = "scheduleId"
            override var screenTime: OffsetDateTime? = OffsetDateTime.now().plusDays(1)
            override var movie: Movie? = null
            override var auditorium: Auditorium? = null
            override var price: BigDecimal = BigDecimal.ZERO
        }
        val startTime = OffsetDateTime.now().plusDays(1)
        val price = BigDecimal.TEN
        val movieLength = 120
        val alreadyScheduled = listOf(object : MovieSchedule {
            override var id: String? = "otherScheduleId"
            override var screenTime: OffsetDateTime? = startTime.plusMinutes(30)
            override var movie: Movie? = null
            override var auditorium: Auditorium? = null
            override var price: BigDecimal = BigDecimal.ZERO
        })

        assertThrows(BusinessRuleException::class.java) {
            updateMovieScheduleUseCase.updateSchedule(
                existingSchedule, startTime, price, movieLength, alreadyScheduled
            )
        }
    }

    @Test
    fun `updateSchedule should allow non-overlapping schedules`() {
        val existingSchedule = object : MovieSchedule {
            override var id: String? = "scheduleId"
            override var screenTime: OffsetDateTime? = OffsetDateTime.now().plusDays(1)
            override var movie: Movie? = null
            override var auditorium: Auditorium? = null
            override var price: BigDecimal = BigDecimal.ZERO
        }
        val startTime = OffsetDateTime.now().plusDays(2)
        val price = BigDecimal.TEN
        val movieLength = 120
        val alreadyScheduled = listOf(object : MovieSchedule {
            override var id: String? = "otherScheduleId"
            override var screenTime: OffsetDateTime? = startTime.minusMinutes(150)
            override var movie: Movie? = null
            override var auditorium: Auditorium? = null
            override var price: BigDecimal = BigDecimal.ZERO
        })

        val updatedSchedule = updateMovieScheduleUseCase.updateSchedule(
            existingSchedule, startTime, price, movieLength, alreadyScheduled
        )

        assertEquals(startTime, updatedSchedule.screenTime)
        assertEquals(price, updatedSchedule.price)
    }
}

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

class CreateMovieScheduleUseCaseTest {

    private val createMovieScheduleUseCase = CreateMovieScheduleUseCase()

    @Test
    fun `createSchedule should return MovieSchedule with correct properties`() {
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
        val startTime = OffsetDateTime.now().plusDays(1)
        val price = BigDecimal.TEN
        val movieLength = 120
        val movieScheduleInstance = object : MovieSchedule {
            override var id: String? = null
            override var screenTime: OffsetDateTime? = null
            override var movie: Movie? = null
            override var auditorium: Auditorium? = null
            override var price: BigDecimal = BigDecimal.ZERO
        }

        val instanceSupplier = { movieScheduleInstance }

        val result = createMovieScheduleUseCase.createSchedule(
            auditorium, movie, startTime, price, movieLength, emptyList(), instanceSupplier
        )

        assertEquals(auditorium, result.auditorium)
        assertEquals(movie, result.movie)
        assertEquals(price, result.price)
        assertEquals(startTime, result.screenTime)
    }

    @Test
    fun `createSchedule should throw InvalidStateException for start time in the past`() {
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
        val startTime = OffsetDateTime.now().minusDays(1)
        val price = BigDecimal.TEN
        val movieLength = 120

        val instanceSupplier = {
            object : MovieSchedule {
                override var id: String? = null
                override var screenTime: OffsetDateTime? = null
                override var movie: Movie? = null
                override var auditorium: Auditorium? = null
                override var price: BigDecimal = BigDecimal.ZERO
            }
        }

        assertThrows(InvalidStateException::class.java) {
            createMovieScheduleUseCase.createSchedule(
                auditorium, movie, startTime, price, movieLength, emptyList(), instanceSupplier
            )
        }
    }

    @Test
    fun `createSchedule should throw BusinessRuleException for overlapping schedules`() {
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
        val startTime = OffsetDateTime.now().plusDays(1)
        val price = BigDecimal.TEN
        val movieLength = 120
        val alreadyScheduled = listOf(object : MovieSchedule {
            override var id: String? = "existingScheduleId"
            override var screenTime: OffsetDateTime? = startTime.plusMinutes(30)
            override var movie: Movie? = null
            override var auditorium: Auditorium? = null
            override var price: BigDecimal = BigDecimal.ZERO
        })

        val instanceSupplier = {
            object : MovieSchedule {
                override var id: String? = null
                override var screenTime: OffsetDateTime? = null
                override var movie: Movie? = null
                override var auditorium: Auditorium? = null
                override var price: BigDecimal = BigDecimal.ZERO
            }
        }

        assertThrows(BusinessRuleException::class.java) {
            createMovieScheduleUseCase.createSchedule(
                auditorium, movie, startTime, price, movieLength, alreadyScheduled, instanceSupplier
            )
        }
    }

    @Test
    fun `createSchedule should allow non-overlapping schedules`() {
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
        val startTime = OffsetDateTime.now().plusDays(1)
        val price = BigDecimal.TEN
        val movieLength = 120
        val alreadyScheduled = listOf(object : MovieSchedule {
            override var id: String? = "existingScheduleId"
            override var screenTime: OffsetDateTime? = startTime.minusMinutes(150)
            override var movie: Movie? = null
            override var auditorium: Auditorium? = null
            override var price: BigDecimal = BigDecimal.ZERO
        })

        val movieScheduleInstance = object : MovieSchedule {
            override var id: String? = null
            override var screenTime: OffsetDateTime? = null
            override var movie: Movie? = null
            override var auditorium: Auditorium? = null
            override var price: BigDecimal = BigDecimal.ZERO
        }

        val instanceSupplier = { movieScheduleInstance }

        val result = createMovieScheduleUseCase.createSchedule(
            auditorium, movie, startTime, price, movieLength, alreadyScheduled, instanceSupplier
        )

        assertEquals(auditorium, result.auditorium)
        assertEquals(movie, result.movie)
        assertEquals(price, result.price)
        assertEquals(startTime, result.screenTime)
    }
}

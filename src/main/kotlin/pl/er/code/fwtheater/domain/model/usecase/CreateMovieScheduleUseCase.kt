package pl.er.code.fwtheater.domain.model.usecase

import pl.er.code.fwtheater.domain.exception.BusinessRuleException
import pl.er.code.fwtheater.domain.exception.InvalidStateException
import pl.er.code.fwtheater.domain.model.Auditorium
import pl.er.code.fwtheater.domain.model.Movie
import pl.er.code.fwtheater.domain.model.MovieSchedule
import java.math.BigDecimal
import java.time.OffsetDateTime

class CreateMovieScheduleUseCase {

    fun createSchedule(
        auditorium: Auditorium,
        movie: Movie,
        startTime: OffsetDateTime,
        price: BigDecimal,
        movieLength: Int,
        alreadyScheduled: List<MovieSchedule>,
        instanceSupplier: () -> MovieSchedule
    ): MovieSchedule {
        validateSchedule(startTime, alreadyScheduled, movieLength)

        val newSchedule: MovieSchedule = instanceSupplier.invoke()
        newSchedule.auditorium = auditorium
        newSchedule.movie = movie
        newSchedule.price = price
        newSchedule.screenTime = startTime

        return newSchedule
    }

    private fun validateSchedule(startTime: OffsetDateTime, alreadyScheduled: List<MovieSchedule>, movieLength: Int) {
        validateStartDateIsNotInThePast(startTime)
        validateThereAreNoScheduleOverlaps(startTime, alreadyScheduled, movieLength)
    }

    private fun validateStartDateIsNotInThePast(startTime: OffsetDateTime) {
        if (startTime.isBefore(OffsetDateTime.now())) {
            throw InvalidStateException("The movie start day can't be in the past!")
        }
    }

    private fun validateThereAreNoScheduleOverlaps(
        startTime: OffsetDateTime,
        alreadyScheduled: List<MovieSchedule>,
        movieLength: Int
    ) {
        val movieEndTime: OffsetDateTime = startTime.plusMinutes(movieLength.toLong())

        val overlapsExist: Boolean = alreadyScheduled.any { schedule ->
            (schedule.screenTime!!.isAfter(startTime) && schedule.screenTime!!.isBefore(movieEndTime)) || schedule.screenTime!!.equals(
                startTime
            )
        }

        if (overlapsExist) {
            throw BusinessRuleException("Schedule can't be created at this time as there are other movies pending at that time.")
        }
    }


}
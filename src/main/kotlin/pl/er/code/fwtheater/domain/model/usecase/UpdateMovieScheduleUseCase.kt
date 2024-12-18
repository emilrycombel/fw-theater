package pl.er.code.fwtheater.domain.model.usecase

import pl.er.code.fwtheater.domain.exception.BusinessRuleException
import pl.er.code.fwtheater.domain.exception.InvalidStateException
import pl.er.code.fwtheater.domain.model.MovieSchedule
import java.math.BigDecimal
import java.time.OffsetDateTime

class UpdateMovieScheduleUseCase {
    fun updateSchedule(
        toUpdate: MovieSchedule,
        startTime: OffsetDateTime,
        price: BigDecimal,
        movieLength: Int,
        alreadyScheduled: List<MovieSchedule>,
    ): MovieSchedule {
        validateSchedule(toUpdate, startTime, alreadyScheduled, movieLength)

        toUpdate.price = price
        toUpdate.screenTime = startTime

        return toUpdate
    }

    private fun validateSchedule(
        toUpdate: MovieSchedule,
        startTime: OffsetDateTime,
        alreadyScheduled: List<MovieSchedule>,
        movieLength: Int
    ) {
        validateStartDateIsNotInThePast(startTime)
        validateThereAreNoScheduleOverlaps(toUpdate, startTime, alreadyScheduled, movieLength)
    }

    private fun validateStartDateIsNotInThePast(startTime: OffsetDateTime) {
        if (startTime.isBefore(OffsetDateTime.now())) {
            throw InvalidStateException("The movie start day can't be in the past!")
        }
    }

    private fun validateThereAreNoScheduleOverlaps(
        toUpdate: MovieSchedule,
        startTime: OffsetDateTime,
        alreadyScheduled: List<MovieSchedule>,
        movieLength: Int
    ) {
        val movieEndTime: OffsetDateTime = startTime.plusMinutes(movieLength.toLong())

        val overlapsExist: Boolean = alreadyScheduled.any { schedule ->
            (schedule.screenTime!!.isAfter(startTime) && schedule.screenTime!!.isBefore(movieEndTime)) || schedule.screenTime!!.equals(
                startTime
            ) && !schedule.id.equals(toUpdate.id)
        }

        if (overlapsExist) {
            throw BusinessRuleException("Schedule can't be updated. Please check the times for the day as it seems there's a movie clash.")
        }
    }


}
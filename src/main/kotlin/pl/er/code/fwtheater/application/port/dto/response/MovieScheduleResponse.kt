package pl.er.code.fwtheater.application.port.dto.response

import pl.er.code.fwtheater.domain.model.MovieSchedule
import java.math.BigDecimal
import java.time.OffsetDateTime

data class MovieScheduleResponse(
    val id: String,
    val title: String,
    val showTime: OffsetDateTime,
    val price: BigDecimal
) {
    companion object {
        fun fromDomain(movieSchedule: MovieSchedule): MovieScheduleResponse {
            return MovieScheduleResponse(
                id = movieSchedule.id!!,
                title = movieSchedule.movie?.title!!,
                showTime = movieSchedule.screenTime!!,
                price = movieSchedule.price
            )
        }
    }
}
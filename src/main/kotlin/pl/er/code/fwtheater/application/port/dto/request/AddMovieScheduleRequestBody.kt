package pl.er.code.fwtheater.application.port.dto.request

import java.math.BigDecimal
import java.time.OffsetDateTime

data class AddMovieScheduleRequestBody(
    val screenTime: OffsetDateTime,
    val price: BigDecimal,
    val auditoriumId: String,
    val movieId: String
)
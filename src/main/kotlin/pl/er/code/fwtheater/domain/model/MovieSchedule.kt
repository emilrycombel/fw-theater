package pl.er.code.fwtheater.domain.model

import java.math.BigDecimal
import java.time.OffsetDateTime


interface MovieSchedule : DomainInstance<String> {
    var screenTime: OffsetDateTime?
    var movie: Movie?
    var auditorium: Auditorium?
    var price: BigDecimal
}
package pl.er.code.fwtheater.application.port.outbound

import pl.er.code.fwtheater.domain.model.MovieSchedule
import pl.er.code.fwtheater.domain.model.search.MovieScheduleSearchCriteria
import java.time.LocalDate

interface MovieScheduleRepository : BaseDomainRepository<MovieSchedule, String, MovieScheduleSearchCriteria> {
    fun findMovieSchedules(auditoriumId: String, day: LocalDate): List<MovieSchedule>
}
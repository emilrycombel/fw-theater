package pl.er.code.fwtheater.adapter.outbound.persistence.repository

import jakarta.persistence.EntityManager
import pl.er.code.fwtheater.adapter.outbound.persistence.entity.MovieScheduleHEntity
import pl.er.code.fwtheater.application.port.outbound.MovieScheduleRepository
import pl.er.code.fwtheater.domain.model.MovieSchedule
import pl.er.code.fwtheater.domain.model.search.MovieScheduleSearchCriteria
import java.time.LocalDate

class MovieScheduleHRepositoryImpl(jpaEntityClass: Class<MovieScheduleHEntity>, entityManager: EntityManager) :
    AbstractDomainRepository<MovieSchedule, MovieScheduleHEntity, String, MovieScheduleSearchCriteria>(
        jpaEntityClass,
        entityManager
    ),
    MovieScheduleRepository {

    override fun findMovieSchedules(auditoriumId: String, day: LocalDate): List<MovieSchedule> {
        val schedules = entityManager.createNamedQuery(
            MovieScheduleHEntity.FIND_SCHEDULES_FOR_GIVEN_DAY_IN_AUDITORIUM,
            MovieScheduleHEntity::class.java
        )
            .setParameter("auditorium_id", auditoriumId)
            .setParameter("day", day)
            .resultList

        return schedules
    }
}
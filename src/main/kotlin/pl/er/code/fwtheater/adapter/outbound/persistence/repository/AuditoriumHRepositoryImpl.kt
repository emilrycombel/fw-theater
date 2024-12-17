package pl.er.code.fwtheater.adapter.outbound.persistence.repository

import jakarta.persistence.EntityManager
import pl.er.code.fwtheater.adapter.outbound.persistence.entity.AuditoriumHEntity
import pl.er.code.fwtheater.application.port.outbound.AuditoriumRepository
import pl.er.code.fwtheater.domain.model.Auditorium
import pl.er.code.fwtheater.domain.model.search.PageSearchCriteria

class AuditoriumHRepositoryImpl(jpaEntityClass: Class<AuditoriumHEntity>, entityManager: EntityManager) :
    AbstractDomainRepository<Auditorium, AuditoriumHEntity, String, PageSearchCriteria>(
        jpaEntityClass,
        entityManager
    ),
    AuditoriumRepository {
}
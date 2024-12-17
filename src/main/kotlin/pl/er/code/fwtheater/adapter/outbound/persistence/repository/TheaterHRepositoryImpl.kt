package pl.er.code.fwtheater.adapter.outbound.persistence.repository

import jakarta.persistence.EntityManager
import pl.er.code.fwtheater.adapter.outbound.persistence.entity.TheaterHEntity
import pl.er.code.fwtheater.application.port.outbound.TheaterRepository
import pl.er.code.fwtheater.domain.model.Theater
import pl.er.code.fwtheater.domain.model.search.PageSearchCriteria

class TheaterHRepositoryImpl(jpaEntityClass: Class<TheaterHEntity>, entityManager: EntityManager) :
    AbstractDomainRepository<Theater, TheaterHEntity, String, PageSearchCriteria>(jpaEntityClass, entityManager),
    TheaterRepository {


}
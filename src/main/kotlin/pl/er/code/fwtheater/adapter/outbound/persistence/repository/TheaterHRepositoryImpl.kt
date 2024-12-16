package pl.er.code.fwtheater.adapter.outbound.persistence.repository

import jakarta.persistence.EntityManager
import pl.er.code.fwtheater.adapter.outbound.persistence.entity.TheaterHEntity
import pl.er.code.fwtheater.domain.model.Theater

class TheaterHRepositoryImpl(jpaEntityClass: Class<TheaterHEntity>, entityManager: EntityManager) :
    AbstractDomainRepository<Theater, TheaterHEntity, String>(jpaEntityClass, entityManager) {
        
}
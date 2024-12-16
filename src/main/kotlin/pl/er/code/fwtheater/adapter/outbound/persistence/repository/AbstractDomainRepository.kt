package pl.er.code.fwtheater.adapter.outbound.persistence.repository

import jakarta.persistence.EntityManager
import org.springframework.data.jpa.repository.support.SimpleJpaRepository
import org.springframework.transaction.annotation.Transactional
import java.io.Serializable

@Transactional(readOnly = true)
abstract class AbstractDomainRepository<D, E : D, ID : Serializable> {

    private lateinit var delegate: SimpleJpaRepository<E, ID>
    private lateinit var entityManger: EntityManager
    private lateinit var entityClass: Class<E>

    /*
        constructor(entityInformation: JpaEntityInformation<E, *>, entityManager: EntityManager) {
            delegate = SimpleJpaRepository(entityInformation, entityManager)
            this.entityManger = entityManager
        }
    */
    constructor(jpaEntityClass: Class<E>, entityManager: EntityManager) {
        delegate = SimpleJpaRepository(jpaEntityClass, entityManager)
        this.entityManger = entityManager
        this.entityClass = jpaEntityClass
    }

    protected fun getEntityManager(): EntityManager {
        return entityManger;
    }

    fun findById(id: ID): D? {
        return delegate.findById(id).orElse(null)
    }

    fun save(instance: D): D {
        return entityClass.cast(delegate.save(entityClass.cast(instance)!!))
    }

    fun saveAndFlush(instance: D): D {
        return entityClass.cast(delegate.saveAndFlush(entityClass.cast(instance)!!))
    }

    fun deleteById(id: ID) {
        delegate.deleteById(id)
    }

    fun delete(instance: D) {
        delegate.delete(entityClass.cast(instance)!!)
    }
    
    fun flush() {
        entityManger.flush()
    }

    fun cleanUp() {
        flush()
        entityManger.clear()
    }

    fun newInstance(): D {
        return entityClass.getDeclaredConstructor().newInstance()
    }

}
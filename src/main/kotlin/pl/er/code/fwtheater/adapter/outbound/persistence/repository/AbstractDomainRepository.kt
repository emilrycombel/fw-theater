package pl.er.code.fwtheater.adapter.outbound.persistence.repository

import jakarta.persistence.EntityManager
import org.springframework.data.jpa.repository.support.SimpleJpaRepository
import org.springframework.transaction.annotation.Transactional
import java.io.Serializable

@Transactional(readOnly = true)
abstract class AbstractDomainRepository<D, E : D, ID : Serializable>(
    val jpaEntityClass: Class<E>,
    protected var entityManager: EntityManager
) {

    private lateinit var delegate: SimpleJpaRepository<E, ID>

    init {
        delegate = SimpleJpaRepository(jpaEntityClass, entityManager)
    }

    fun findById(id: ID): D? {
        return delegate.findById(id).orElse(null)
    }

    fun save(instance: D): D {
        return jpaEntityClass.cast(delegate.save(jpaEntityClass.cast(instance)!!))
    }

    fun saveAndFlush(instance: D): D {
        return jpaEntityClass.cast(delegate.saveAndFlush(jpaEntityClass.cast(instance)!!))
    }

    fun deleteById(id: ID) {
        delegate.deleteById(id)
    }

    fun delete(instance: D) {
        delegate.delete(jpaEntityClass.cast(instance)!!)
    }

    fun flush() {
        entityManager.flush()
    }

    fun cleanUp() {
        flush()
        entityManager.clear()
    }

    fun newInstance(): D {
        return jpaEntityClass.getDeclaredConstructor().newInstance()
    }

}
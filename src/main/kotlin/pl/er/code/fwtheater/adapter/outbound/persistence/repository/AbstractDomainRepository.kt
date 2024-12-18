package pl.er.code.fwtheater.adapter.outbound.persistence.repository

import jakarta.persistence.EntityManager
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.support.SimpleJpaRepository
import org.springframework.transaction.annotation.Transactional
import pl.er.code.fwtheater.application.port.outbound.BaseDomainRepository
import pl.er.code.fwtheater.domain.model.DomainInstance
import pl.er.code.fwtheater.domain.model.Page
import pl.er.code.fwtheater.domain.model.search.PageSearchCriteria
import java.io.Serializable

@Transactional(readOnly = true)
abstract class AbstractDomainRepository<D : DomainInstance<ID>, E : D, ID : Serializable, S : PageSearchCriteria>(
    val jpaEntityClass: Class<E>,
    protected var entityManager: EntityManager
) : BaseDomainRepository<D, ID, S> {

    private lateinit var delegate: SimpleJpaRepository<E, ID>

    init {
        delegate = SimpleJpaRepository(jpaEntityClass, entityManager)
    }

    override fun findById(id: ID): D? {
        return delegate.findById(id).orElse(null)
    }

    override fun save(instance: D): D {
        return jpaEntityClass.cast(delegate.save(jpaEntityClass.cast(instance)!!))
    }

    override fun saveAndFlush(instance: D): D {
        return jpaEntityClass.cast(delegate.saveAndFlush(jpaEntityClass.cast(instance)!!))
    }

    override fun deleteById(id: ID) {
        delegate.deleteById(id)
    }

    override fun delete(instance: D) {
        delegate.delete(jpaEntityClass.cast(instance)!!)
    }

    override fun flush() {
        entityManager.flush()
    }

    override fun cleanUp() {
        flush()
        entityManager.clear()
    }

    override fun newInstance(): D {
        return jpaEntityClass.getDeclaredConstructor().newInstance()
    }

    override fun search(searchRequest: S): Page<D> {
        val pageResult = delegate.findAll(
            PageRequest.of(
                searchRequest.page,
                searchRequest.size,
                Sort.by(Sort.Direction.ASC, "id")
            )
        )

        return Page(
            numberOfElements = pageResult.numberOfElements,
            size = pageResult.size,
            totalElements = pageResult.totalElements,
            number = pageResult.number,
            content = pageResult.content,
            totalPages = pageResult.totalPages
        )
    }

    protected fun getDelegate(): SimpleJpaRepository<E, ID> {
        return delegate
    }
}
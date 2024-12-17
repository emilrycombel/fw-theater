package pl.er.code.fwtheater.application.service

import org.springframework.transaction.annotation.Transactional
import pl.er.code.fwtheater.application.port.outbound.BaseDomainRepository
import pl.er.code.fwtheater.domain.model.DomainInstance
import pl.er.code.fwtheater.domain.model.Page
import pl.er.code.fwtheater.domain.model.search.PageSearchCriteria
import java.io.Serializable

abstract class AbstractRepositoryBasedService<D : DomainInstance<ID>, E : D, ID : Serializable, S : PageSearchCriteria, R : BaseDomainRepository<D, ID, S>>(
    private val repository: R
) {
    @Transactional(readOnly = true)
    open fun <U> search(searchCriteria: S, converter: (D) -> U): Page<U> {
        return repository.search(searchCriteria).map(converter)
    }

    @Transactional(readOnly = true)
    open fun <U> findById(id: ID, converter: (D) -> U): U? {
        return repository.findById(id)?.let { converter.invoke(it) }
    }

    fun newInstance(): D {
        return repository.newInstance()
    }
}
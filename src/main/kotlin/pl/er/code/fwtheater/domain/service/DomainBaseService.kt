package pl.er.code.fwtheater.domain.service

import pl.er.code.fwtheater.domain.model.DomainInstance
import pl.er.code.fwtheater.domain.model.Page
import pl.er.code.fwtheater.domain.model.search.PageSearchCriteria
import java.io.Serializable

interface DomainBaseService<ID : Serializable, D : DomainInstance<ID>, S : PageSearchCriteria> {
    fun <U> search(searchCriteria: S, converter: (D) -> U): Page<U>
    fun search(searchCriteria: S): Page<D> {
        return search(searchCriteria) { it -> it }
    }

    fun <U> findById(id: ID, converter: (D) -> U): U?
    fun <U> findById(id: ID): D? {
        return findById(id) { it -> it }
    }
}
package pl.er.code.fwtheater.application.port.outbound

import pl.er.code.fwtheater.domain.model.DomainInstance
import pl.er.code.fwtheater.domain.model.Page
import pl.er.code.fwtheater.domain.model.search.PageSearchCriteria
import java.io.Serializable

interface BaseDomainRepository<D : DomainInstance<ID>, ID : Serializable, S : PageSearchCriteria> {
    fun findById(id: ID): D?
    fun save(instance: D): D
    fun saveAndFlush(instance: D): D
    fun deleteById(id: ID)
    fun delete(instance: D)
    fun flush()
    fun cleanUp()
    fun newInstance(): D
    fun search(searchRequest: S): Page<D>
}
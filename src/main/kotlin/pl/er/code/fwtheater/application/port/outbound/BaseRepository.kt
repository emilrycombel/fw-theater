package pl.er.code.fwtheater.application.port.outbound

import pl.er.code.fwtheater.domain.model.DomainInstance
import java.io.Serializable

interface BaseRepository<D : DomainInstance<ID>, ID : Serializable> {
    fun findById(id: ID): D?
    fun save(instance: D): D
    fun saveAndFlush(instance: D): D
    fun deleteById(id: ID)
    fun delete(instance: D)
    fun flush()
    fun cleanUp()
    fun newInstance(): D
}
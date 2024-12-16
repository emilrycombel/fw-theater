package pl.er.code.fwtheater.adapter.outbound.persistence.entity

import java.io.Serializable

interface Persistable<ID : Serializable> {
    var id: ID?
    fun isNew(): Boolean
}
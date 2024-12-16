package pl.er.code.fwtheater.adapter.outbound.persistence.entity

import java.io.Serializable

interface Identifiable<ID : Serializable> {
    fun getEntityId(): ID?;
}
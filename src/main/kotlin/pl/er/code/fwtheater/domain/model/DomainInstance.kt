package pl.er.code.fwtheater.domain.model

import java.io.Serializable

interface DomainInstance<ID : Serializable> {
    var id: ID?
}
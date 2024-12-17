package pl.er.code.fwtheater.application.port.outbound

import pl.er.code.fwtheater.domain.model.Theater
import pl.er.code.fwtheater.domain.model.search.PageSearchCriteria

interface TheaterRepository : BaseDomainRepository<Theater, String, PageSearchCriteria> {
}
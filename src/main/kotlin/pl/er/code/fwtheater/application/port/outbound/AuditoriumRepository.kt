package pl.er.code.fwtheater.application.port.outbound

import pl.er.code.fwtheater.domain.model.Auditorium
import pl.er.code.fwtheater.domain.model.search.PageSearchCriteria

interface AuditoriumRepository : BaseDomainRepository<Auditorium, String, PageSearchCriteria> {
}
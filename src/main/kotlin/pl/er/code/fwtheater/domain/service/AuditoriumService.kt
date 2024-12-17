package pl.er.code.fwtheater.domain.service

import pl.er.code.fwtheater.domain.model.Auditorium
import pl.er.code.fwtheater.domain.model.search.PageSearchCriteria

interface AuditoriumService : DomainBaseService<String, Auditorium, PageSearchCriteria> {
}
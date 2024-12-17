package pl.er.code.fwtheater.application.service

import pl.er.code.fwtheater.adapter.outbound.persistence.entity.AuditoriumHEntity
import pl.er.code.fwtheater.application.port.outbound.AuditoriumRepository
import pl.er.code.fwtheater.domain.model.Auditorium
import pl.er.code.fwtheater.domain.model.search.PageSearchCriteria
import pl.er.code.fwtheater.domain.service.AuditoriumService

class AuditoriumServiceImpl(private val auditoriumRepository: AuditoriumRepository) :
    AbstractRepositoryBasedService<Auditorium, AuditoriumHEntity, String, PageSearchCriteria, AuditoriumRepository>(
        auditoriumRepository
    ),
    AuditoriumService {

}
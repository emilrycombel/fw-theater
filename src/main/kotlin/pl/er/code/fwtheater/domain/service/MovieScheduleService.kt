package pl.er.code.fwtheater.domain.service

import pl.er.code.fwtheater.application.port.dto.request.AddMovieScheduleRequestBody
import pl.er.code.fwtheater.domain.model.MovieSchedule
import pl.er.code.fwtheater.domain.model.search.MovieScheduleSearchCriteria

interface MovieScheduleService : DomainBaseService<String, MovieSchedule, MovieScheduleSearchCriteria> {
    fun addSchedule(addMovieScheduleRequestBody: AddMovieScheduleRequestBody): MovieSchedule
}
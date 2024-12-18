package pl.er.code.fwtheater.domain.service

import pl.er.code.fwtheater.application.port.dto.request.AddMovieRatingRequestBody
import pl.er.code.fwtheater.domain.model.MovieRating
import pl.er.code.fwtheater.domain.model.search.MovieRatingSearchCriteria

interface MovieRatingService : DomainBaseService<String, MovieRating, MovieRatingSearchCriteria> {
    fun addRating(addMovieRatingRequestBody: AddMovieRatingRequestBody): MovieRating
}
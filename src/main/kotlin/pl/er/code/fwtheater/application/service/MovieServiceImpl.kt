package pl.er.code.fwtheater.application.service

import pl.er.code.fwtheater.adapter.outbound.persistence.entity.MovieHEntity
import pl.er.code.fwtheater.application.port.outbound.MovieRepository
import pl.er.code.fwtheater.domain.model.Movie
import pl.er.code.fwtheater.domain.model.search.MovieSearchCriteria
import pl.er.code.fwtheater.domain.service.MovieService

open class MovieServiceImpl(private val movieRepository: MovieRepository) :
    AbstractRepositoryBasedService<Movie, MovieHEntity, String, MovieSearchCriteria, MovieRepository>(
        movieRepository
    ),
    MovieService {
}

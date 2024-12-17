package pl.er.code.fwtheater.domain.service

import pl.er.code.fwtheater.domain.model.Movie
import pl.er.code.fwtheater.domain.model.search.MovieSearchCriteria

interface MovieService : DomainBaseService<String, Movie, MovieSearchCriteria> {
}
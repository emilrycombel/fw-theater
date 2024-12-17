package pl.er.code.fwtheater.application.port.outbound

import pl.er.code.fwtheater.domain.model.Movie
import pl.er.code.fwtheater.domain.model.search.MovieSearchCriteria

interface MovieRepository : BaseDomainRepository<Movie, String, MovieSearchCriteria> {
}
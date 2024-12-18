package pl.er.code.fwtheater.application.port.outbound

import pl.er.code.fwtheater.domain.model.MovieRating
import pl.er.code.fwtheater.domain.model.search.MovieRatingSearchCriteria

interface MovieRatingRepository : BaseDomainRepository<MovieRating, String, MovieRatingSearchCriteria> {
}
package pl.er.code.fwtheater.application.port.outbound

import pl.er.code.fwtheater.domain.model.FilmBaseMovie
import pl.er.code.fwtheater.domain.model.search.PageSearchCriteria

interface FilmBaseMovieRepository : BaseDomainRepository<FilmBaseMovie, String, PageSearchCriteria> {
    fun findOneByFilmBaseCodeAndMovie(filmBaseCode: String, movieId: String): FilmBaseMovie?
}
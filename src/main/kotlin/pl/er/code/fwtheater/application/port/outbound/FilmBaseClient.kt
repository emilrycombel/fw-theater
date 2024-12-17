package pl.er.code.fwtheater.application.port.outbound

import pl.er.code.fwtheater.domain.model.FilmBaseMovieDetails

interface FilmBaseClient {

    fun movieDetailsFor(filmBaseMovieId: String): FilmBaseMovieDetails?
}
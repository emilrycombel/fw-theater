package pl.er.code.fwtheater.domain.service

import pl.er.code.fwtheater.domain.model.FilmBaseMovieDetails

interface FilmBaseService {
    fun getMovieDetails(movieId: String): FilmBaseMovieDetails?
}
package pl.er.code.fwtheater.application.service

import pl.er.code.fwtheater.adapter.outbound.rest.OmdbRestClient
import pl.er.code.fwtheater.application.port.outbound.FilmBaseMovieRepository
import pl.er.code.fwtheater.domain.model.FilmBaseMovie
import pl.er.code.fwtheater.domain.model.FilmBaseMovieDetails
import pl.er.code.fwtheater.domain.service.FilmBaseService

class MovieDetailsServiceImpl(
    private val omdbRestClient: OmdbRestClient,
    private val filmBaseMovieRepository: FilmBaseMovieRepository
) : FilmBaseService {
    override fun getMovieDetails(movieId: String): FilmBaseMovieDetails? {
        val filmBaseMovie: FilmBaseMovie? = filmBaseMovieRepository.findOneByFilmBaseCodeAndMovie("OMDB", movieId)

        return filmBaseMovie?.let {
            omdbRestClient.movieDetailsFor(filmBaseMovie.platformMovieId!!)
        }
    }
}
package pl.er.code.fwtheater.application.service

import org.springframework.transaction.annotation.Transactional
import pl.er.code.fwtheater.adapter.outbound.persistence.entity.MovieRatingHEntity
import pl.er.code.fwtheater.application.port.dto.request.AddMovieRatingRequestBody
import pl.er.code.fwtheater.application.port.outbound.MovieRatingRepository
import pl.er.code.fwtheater.domain.exception.InvalidStateException
import pl.er.code.fwtheater.domain.model.Movie
import pl.er.code.fwtheater.domain.model.MovieRating
import pl.er.code.fwtheater.domain.model.search.MovieRatingSearchCriteria
import pl.er.code.fwtheater.domain.service.MovieRatingService
import pl.er.code.fwtheater.domain.service.MovieService
import pl.er.code.fwtheater.domain.usecase.CreateMovieRatingUseCase

open class MovieRatingServiceImpl(
    private val movieRatingRepository: MovieRatingRepository,
    private val movieService: MovieService,
    private val createMovieRatingUseCase: CreateMovieRatingUseCase
) :
    AbstractRepositoryBasedService<MovieRating, MovieRatingHEntity, String, MovieRatingSearchCriteria, MovieRatingRepository>(
        movieRatingRepository
    ),
    MovieRatingService {

    @Transactional(readOnly = false)
    override fun addRating(addMovieRatingRequestBody: AddMovieRatingRequestBody): MovieRating {
        val movie: Movie = movieService.findById<Movie>(addMovieRatingRequestBody.movieId)
            ?: throw InvalidStateException("Movie with ID ${addMovieRatingRequestBody.movieId} wasn't found. Are you sure you have the correct id ?")

        return movieRatingRepository.save(createMovieRatingUseCase.createMovieRating(
            movie = movie,
            rating = addMovieRatingRequestBody.rating,
            ratingUser = addMovieRatingRequestBody.ratingUser,
            comment = addMovieRatingRequestBody.comment,
            instanceSupplier = { newInstance() }
        ))
    }
}
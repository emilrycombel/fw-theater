package pl.er.code.fwtheater.application.service

import org.springframework.transaction.annotation.Transactional
import pl.er.code.fwtheater.adapter.outbound.persistence.entity.MovieScheduleHEntity
import pl.er.code.fwtheater.adapter.outbound.rest.OmdbRestClient
import pl.er.code.fwtheater.application.port.dto.request.AddMovieScheduleRequestBody
import pl.er.code.fwtheater.application.port.outbound.FilmBaseMovieRepository
import pl.er.code.fwtheater.application.port.outbound.MovieScheduleRepository
import pl.er.code.fwtheater.domain.model.Auditorium
import pl.er.code.fwtheater.domain.model.Movie
import pl.er.code.fwtheater.domain.model.MovieSchedule
import pl.er.code.fwtheater.domain.model.search.MovieScheduleSearchCriteria
import pl.er.code.fwtheater.domain.model.usecase.CreateMovieScheduleUseCase
import pl.er.code.fwtheater.domain.service.AuditoriumService
import pl.er.code.fwtheater.domain.service.MovieScheduleService
import pl.er.code.fwtheater.domain.service.MovieService
import java.time.LocalDate

open class MovieScheduleServiceImpl(
    private val movieScheduleRepository: MovieScheduleRepository,
    private val movieService: MovieService,
    private val auditoriumService: AuditoriumService,
    private val createMovieScheduleUseCase: CreateMovieScheduleUseCase,
    private val omdbRestClient: OmdbRestClient,
    private val filmBaseMovieRepository: FilmBaseMovieRepository
) :
    AbstractRepositoryBasedService<MovieSchedule, MovieScheduleHEntity, String, MovieScheduleSearchCriteria, MovieScheduleRepository>(
        movieScheduleRepository
    ),
    MovieScheduleService {

    @Transactional(readOnly = false)
    override fun addSchedule(addMovieScheduleRequestBody: AddMovieScheduleRequestBody): MovieSchedule {
        val auditorium: Auditorium? = auditoriumService.findById<Auditorium>(addMovieScheduleRequestBody.auditoriumId)
        val movie: Movie? = movieService.findById<Movie>(addMovieScheduleRequestBody.movieId)
        val filmBaseMovieId = filmBaseMovieRepository.findOneByFilmBaseCodeAndMovie("OMDB", movie?.id!!)
        val movieDetails = omdbRestClient.movieDetailsFor(filmBaseMovieId?.platformMovieId!!)

        val newSchedule: MovieSchedule = createMovieScheduleUseCase.createSchedule(
            auditorium = auditorium!!,
            movie = movie,
            startTime = addMovieScheduleRequestBody.screenTime,
            alreadyScheduled = findMovieSchedulesFor(
                auditorium.id!!,
                addMovieScheduleRequestBody.screenTime.toLocalDate()
            ),
            movieLength = movieDetails?.length!!,
            price = addMovieScheduleRequestBody.price,
            instanceSupplier = { newInstance() }
        )

        return movieScheduleRepository.saveAndFlush(newSchedule)
    }

    private fun findMovieSchedulesFor(
        auditoriumId: String,
        day: LocalDate
    ): List<MovieSchedule> {
        return movieScheduleRepository.findMovieSchedules(auditoriumId, day)
    }

}
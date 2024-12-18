package pl.er.code.fwtheater.infrastructure.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.er.code.fwtheater.adapter.outbound.rest.OmdbRestClient
import pl.er.code.fwtheater.application.port.outbound.AuditoriumRepository
import pl.er.code.fwtheater.application.port.outbound.FilmBaseMovieRepository
import pl.er.code.fwtheater.application.port.outbound.MovieRepository
import pl.er.code.fwtheater.application.port.outbound.MovieScheduleRepository
import pl.er.code.fwtheater.application.service.AuditoriumServiceImpl
import pl.er.code.fwtheater.application.service.MovieScheduleServiceImpl
import pl.er.code.fwtheater.application.service.MovieServiceImpl
import pl.er.code.fwtheater.domain.model.usecase.CreateMovieScheduleUseCase
import pl.er.code.fwtheater.domain.model.usecase.UpdateMovieScheduleUseCase
import pl.er.code.fwtheater.domain.service.AuditoriumService
import pl.er.code.fwtheater.domain.service.MovieScheduleService
import pl.er.code.fwtheater.domain.service.MovieService

@Configuration
class RepositoryBasedServicesConfiguration {


    @Bean
    fun movieService(movieRepository: MovieRepository): MovieService {
        return MovieServiceImpl(movieRepository)
    }

    @Bean
    fun auditoriumService(auditoriumRepository: AuditoriumRepository): AuditoriumService {
        return AuditoriumServiceImpl(auditoriumRepository)
    }

    @Bean
    fun movieScheduleService(
        movieScheduleRepository: MovieScheduleRepository,
        movieService: MovieService,
        auditoriumService: AuditoriumService,
        createMovieScheduleUseCase: CreateMovieScheduleUseCase,
        updateMovieScheduleUseCase: UpdateMovieScheduleUseCase,
        omdbRestClient: OmdbRestClient,
        filmBaseMovieRepository: FilmBaseMovieRepository
    ): MovieScheduleService {
        return MovieScheduleServiceImpl(
            movieScheduleRepository,
            movieService,
            auditoriumService,
            createMovieScheduleUseCase,
            updateMovieScheduleUseCase,
            omdbRestClient,
            filmBaseMovieRepository
        )
    }

}
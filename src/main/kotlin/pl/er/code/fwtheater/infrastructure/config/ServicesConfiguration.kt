package pl.er.code.fwtheater.infrastructure.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.er.code.fwtheater.adapter.outbound.rest.OmdbRestClient
import pl.er.code.fwtheater.application.port.outbound.FilmBaseMovieRepository
import pl.er.code.fwtheater.application.service.MovieDetailsServiceImpl
import pl.er.code.fwtheater.domain.service.FilmBaseService

@Configuration
class ServicesConfiguration {

    @Bean
    fun filmBaseService(
        omdbRestClient: OmdbRestClient,
        movieDetailsRepository: FilmBaseMovieRepository
    ): FilmBaseService {
        return MovieDetailsServiceImpl(omdbRestClient, movieDetailsRepository)
    }


}
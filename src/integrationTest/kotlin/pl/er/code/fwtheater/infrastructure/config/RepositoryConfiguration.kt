package pl.er.code.fwtheater.infrastructure.config

import jakarta.persistence.EntityManager
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import pl.er.code.fwtheater.adapter.outbound.persistence.entity.*
import pl.er.code.fwtheater.adapter.outbound.persistence.repository.*
import pl.er.code.fwtheater.application.port.outbound.*

@TestConfiguration
@ActiveProfiles(AvailableProfiles.WITH_TEST_CONTAINER_DB)
@Import(LiquibaseConfiguration::class)
class RepositoryConfiguration {

    @Bean
    fun theaterRepository(entityManager: EntityManager): TheaterHRepositoryImpl {
        return TheaterHRepositoryImpl(TheaterHEntity::class.java, entityManager)
    }

    @Bean
    fun movieRepository(entityManager: EntityManager): MovieRepository {
        return MovieHRepositoryImpl(MovieHEntity::class.java, entityManager)
    }

    @Bean
    fun auditoriumRepository(entityManager: EntityManager): AuditoriumRepository {
        return AuditoriumHRepositoryImpl(AuditoriumHEntity::class.java, entityManager)
    }

    @Bean
    fun movieSchedulerRepository(entityManager: EntityManager): MovieScheduleRepository {
        return MovieScheduleHRepositoryImpl(MovieScheduleHEntity::class.java, entityManager)
    }

    @Bean
    fun filmBaseMovieRepository(entityManager: EntityManager): FilmBaseMovieRepository {
        return FilmBaseMovieHRepositoryImpl(FilmBaseMovieHEntity::class.java, entityManager)
    }

    @Bean
    fun movieRatingRepository(entityManager: EntityManager): MovieRatingRepository {
        return MovieRatingHRepositoryImpl(MovieRatingHEntity::class.java, entityManager)
    }

}
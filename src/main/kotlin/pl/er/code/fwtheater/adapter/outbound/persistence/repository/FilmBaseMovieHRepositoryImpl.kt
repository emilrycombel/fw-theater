package pl.er.code.fwtheater.adapter.outbound.persistence.repository

import jakarta.persistence.EntityManager
import pl.er.code.fwtheater.adapter.outbound.persistence.entity.FilmBaseMovieHEntity
import pl.er.code.fwtheater.application.port.outbound.FilmBaseMovieRepository
import pl.er.code.fwtheater.domain.model.FilmBaseMovie
import pl.er.code.fwtheater.domain.model.search.PageSearchCriteria

class FilmBaseMovieHRepositoryImpl(jpaEntityClass: Class<FilmBaseMovieHEntity>, entityManager: EntityManager) :
    AbstractDomainRepository<FilmBaseMovie, FilmBaseMovieHEntity, String, PageSearchCriteria>(
        jpaEntityClass,
        entityManager
    ),
    FilmBaseMovieRepository {

    override fun findOneByFilmBaseCodeAndMovie(filmBaseCode: String, movieId: String): FilmBaseMovie? {
        return entityManager.createNamedQuery(
            FilmBaseMovieHEntity.FIND_ONE_BY_FILM_BASE_AND_MOVIE,
            FilmBaseMovieHEntity::class.java
        ).setParameter("filmBaseCode", filmBaseCode).setParameter("movie_id", movieId)
            .singleResult
    }
}
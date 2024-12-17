package pl.er.code.fwtheater.adapter.outbound.persistence.repository

import jakarta.persistence.EntityManager
import pl.er.code.fwtheater.adapter.outbound.persistence.entity.MovieHEntity
import pl.er.code.fwtheater.application.port.outbound.MovieRepository
import pl.er.code.fwtheater.domain.model.Movie
import pl.er.code.fwtheater.domain.model.Page
import pl.er.code.fwtheater.domain.model.search.MovieSearchCriteria

class MovieHRepositoryImpl(jpaEntityClass: Class<MovieHEntity>, entityManager: EntityManager) :
    AbstractDomainRepository<Movie, MovieHEntity, String, MovieSearchCriteria>(jpaEntityClass, entityManager),
    MovieRepository {

    override fun search(searchRequest: MovieSearchCriteria): Page<Movie> {
        var result: Page<Movie>? = null;

        if (searchRequest.title != null) {
            val queryResult = entityManager.createNamedQuery(MovieHEntity.FIND_BY_TITLE, MovieHEntity::class.java)
                .setParameter("title", searchRequest.title)
                .setFirstResult(searchRequest.page * searchRequest.size)
                .setMaxResults(searchRequest.size)
                .resultList

            result = Page.singlePageFromList(queryResult)
        } else if (searchRequest.id != null) {
            val queryResult: Movie? = findById(searchRequest.id)

            result = queryResult?.let { Page.singleItem(queryResult) } ?: Page.empty()
        } else {
            val queryResult = entityManager.createNamedQuery(MovieHEntity.NO_FILTER, MovieHEntity::class.java)
                .setFirstResult(searchRequest.page * searchRequest.size)
                .setMaxResults(searchRequest.size)
                .resultList

            result = Page.singlePageFromList(queryResult)
        }

        return result!!
    }
}
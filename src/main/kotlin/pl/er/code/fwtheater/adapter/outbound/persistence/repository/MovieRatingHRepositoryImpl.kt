package pl.er.code.fwtheater.adapter.outbound.persistence.repository

import jakarta.persistence.EntityManager
import pl.er.code.fwtheater.adapter.outbound.persistence.entity.MovieRatingHEntity
import pl.er.code.fwtheater.adapter.outbound.persistence.entity.MovieRatingHEntity.Companion.FIND_BY_MOVIE_ID
import pl.er.code.fwtheater.application.port.outbound.MovieRatingRepository
import pl.er.code.fwtheater.domain.model.MovieRating
import pl.er.code.fwtheater.domain.model.Page
import pl.er.code.fwtheater.domain.model.search.MovieRatingSearchCriteria

class MovieRatingHRepositoryImpl(jpaEntityClass: Class<MovieRatingHEntity>, entityManager: EntityManager) :
    AbstractDomainRepository<MovieRating, MovieRatingHEntity, String, MovieRatingSearchCriteria>(
        jpaEntityClass,
        entityManager
    ),
    MovieRatingRepository {

    override fun search(searchRequest: MovieRatingSearchCriteria): Page<MovieRating> {
        if (searchRequest.movieId != null) {
            return Page.singlePageFromList(
                entityManager.createNamedQuery(FIND_BY_MOVIE_ID, MovieRatingHEntity::class.java)
                    .setParameter("movie_id", searchRequest.movieId)
                    .setFirstResult(searchRequest.page * searchRequest.size)
                    .setMaxResults(searchRequest.size)
                    .resultList
            )
        } else if (searchRequest.id != null) {
            val queryResult: MovieRating? = findById(searchRequest.id)

            return queryResult?.let { Page.singleItem(queryResult) } ?: Page.empty()
        } else {
            return super.search(searchRequest)
        }
    }
}
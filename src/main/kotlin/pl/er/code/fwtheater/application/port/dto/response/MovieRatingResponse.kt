package pl.er.code.fwtheater.application.port.dto.response

import pl.er.code.fwtheater.domain.model.MovieRating

data class MovieRatingResponse(
    val id: String,
    val ratingScore: Short,
    val comment: String?,
    val ratingUser: String,
    val movieId: String,
    val movieTitle: String,
) {
    companion object {
        fun fromDomain(movieRating: MovieRating): MovieRatingResponse {
            return MovieRatingResponse(
                id = movieRating.id!!,
                movieTitle = movieRating.movie?.title!!,
                comment = movieRating.comment,
                ratingUser = movieRating.ratingUser!!,
                movieId = movieRating.movie?.id!!,
                ratingScore = movieRating.rating!!
            )
        }
    }
}
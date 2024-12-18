package pl.er.code.fwtheater.domain.usecase

import pl.er.code.fwtheater.domain.exception.BusinessRuleException
import pl.er.code.fwtheater.domain.exception.InvalidStateException
import pl.er.code.fwtheater.domain.model.Movie
import pl.er.code.fwtheater.domain.model.MovieRating
import pl.er.code.fwtheater.domain.service.ProfanityService

class CreateMovieRatingUseCase(private var profanityService: ProfanityService?) :
    BaseUseCase() {

    fun createMovieRating(
        movie: Movie,
        rating: Short,
        comment: String?,
        ratingUser: String?,
        instanceSupplier: () -> MovieRating
    ): MovieRating {
        validateRating(rating, comment, ratingUser)

        val movieRating: MovieRating = instanceSupplier.invoke()
        movieRating.movie = movie
        movieRating.rating = rating
        movieRating.comment = comment
        movieRating.ratingUser = ratingUser?.let { ratingUser } ?: "Anonymous"

        return movieRating
    }

    private fun validateRating(rating: Short, comment: String?, ratingUser: String?) {
        validateRating(rating)
        checkForProfanedWords(comment, ratingUser)
    }

    private fun validateRating(rating: Short) {
        if (rating < 1 || rating > 5) {
            throw InvalidStateException("Bad rating ${rating}. Rating must be between 1 & 5")
        }
    }

    private fun checkForProfanedWords(
        comment: String?, ratingUser: String?
    ) {
        if (profanityService == null) {
            log.atWarn().log("Profanity service is not available. Bad words can be placed in the system")
            return
        }

        checkForProfanedWordsInUser(ratingUser, profanityService!!)
        checkForProfanedWordsInComment(comment, profanityService!!)
    }

    private fun checkForProfanedWordsInUser(
        ratingUser: String?,
        profanityService: ProfanityService
    ) {
        if (ratingUser == null || ratingUser.isEmpty()) {
            return
        }

        if (profanityService.hasProfanedText(ratingUser)) {
            throw BusinessRuleException("Rating User has profaned word in the name. Be respectful")
        }
    }

    private fun checkForProfanedWordsInComment(
        comment: String?,
        profanityService: ProfanityService
    ) {
        if (comment == null || comment.isEmpty()) {
            return
        }

        if (profanityService.hasProfanedText(comment)) {
            throw BusinessRuleException("Comment has profaned word in the name. Be respectful")
        }
    }

}
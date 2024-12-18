package pl.er.code.fwtheater.application.port.dto.request

data class AddMovieRatingRequestBody(
    val movieId: String,
    val rating: Short,
    val comment: String?,
    val ratingUser: String?
)
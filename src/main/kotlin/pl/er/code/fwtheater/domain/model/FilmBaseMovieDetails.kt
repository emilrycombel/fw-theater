package pl.er.code.fwtheater.domain.model

data class FilmBaseMovieDetails(
    val name: String,
    val description: String,
    val releaseDate: String,
    val rating: String,
    val imdbRating: String,
    val runtime: String,
    val length: Int
)

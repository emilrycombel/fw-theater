package pl.er.code.fwtheater.adapter.outbound.rest.mapper

import pl.er.code.fwtheater.adapter.outbound.rest.model.OmdbMovieDetailsResponse
import pl.er.code.fwtheater.domain.model.FilmBaseMovieDetails


fun OmdbMovieDetailsResponse.toDomain(): FilmBaseMovieDetails {
    return FilmBaseMovieDetails(
        name = title,
        description = plot,
        releaseDate = released,
        rating = rated,
        imdbRating = imdbRating,
        runtime = runtime,
        length = runtime.split(" ").firstOrNull()?.toIntOrNull() ?: 0
    )
}
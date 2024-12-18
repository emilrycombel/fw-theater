package pl.er.code.fwtheater.adapter.inbound.rest

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pl.er.code.fwtheater.application.port.dto.request.AddMovieRatingRequestBody
import pl.er.code.fwtheater.application.port.dto.response.MovieRatingResponse
import pl.er.code.fwtheater.application.port.dto.response.ResponseEnvelope
import pl.er.code.fwtheater.domain.model.search.MovieRatingSearchCriteria
import pl.er.code.fwtheater.domain.service.MovieRatingService

@RestController
@RequestMapping("/public/api/v1//movies/rating")
class MovieRatingController(@Autowired private val movieRatingService: MovieRatingService) {

    @GetMapping("/list", produces = arrayOf("application/json"))
    @Tag(name = "Public API", description = "APIs accessible without JWT")
    fun list(searchCriteria: MovieRatingSearchCriteria): ResponseEntity<ResponseEnvelope<List<MovieRatingResponse>, String>> {
        val response = ResponseEnvelope.fromPage<MovieRatingResponse, String>(
            movieRatingService.search(searchCriteria) { MovieRatingResponse.fromDomain(it) }
        )

        return ResponseEntity.ok(response)
    }

    @PostMapping("", produces = arrayOf("application/json"), consumes = arrayOf("application/json"))
    @Tag(name = "Public API", description = "APIs accessible without JWT")
    fun post(@RequestBody addMovieRatingRequestBody: AddMovieRatingRequestBody): ResponseEntity<ResponseEnvelope<MovieRatingResponse, String>> {
        return ResponseEntity.ok(
            ResponseEnvelope<MovieRatingResponse, String>(
                data = MovieRatingResponse.fromDomain(movieRatingService.addRating(addMovieRatingRequestBody)),
                status = "success"
            )
        )
    }

}
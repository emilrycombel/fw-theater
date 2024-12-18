package pl.er.code.fwtheater.adapter.inbound.rest

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pl.er.code.fwtheater.application.port.dto.request.AddMovieRatingRequestBody
import pl.er.code.fwtheater.application.port.dto.response.MovieRatingResponse
import pl.er.code.fwtheater.application.port.dto.response.ResponseEnvelope
import pl.er.code.fwtheater.domain.model.search.MovieRatingSearchCriteria
import pl.er.code.fwtheater.domain.service.MovieRatingService

@RestController
@RequestMapping("/movies/rating")
class MovieRatingController(@Autowired private val movieRatingService: MovieRatingService) {

    @GetMapping("/list", produces = arrayOf("application/json"))
    fun list(searchCriteria: MovieRatingSearchCriteria): ResponseEntity<ResponseEnvelope<List<MovieRatingResponse>, String>> {
        val response = ResponseEnvelope.fromPage<MovieRatingResponse, String>(
            movieRatingService.search(searchCriteria) { MovieRatingResponse.fromDomain(it) }
        )

        return ResponseEntity.ok(response)
    }

    @PostMapping("", produces = arrayOf("application/json"), consumes = arrayOf("application/json"))
    fun post(@RequestBody addMovieRatingRequestBody: AddMovieRatingRequestBody): ResponseEntity<ResponseEnvelope<MovieRatingResponse, String>> {
        return ResponseEntity.ok(
            ResponseEnvelope<MovieRatingResponse, String>(
                data = MovieRatingResponse.fromDomain(movieRatingService.addRating(addMovieRatingRequestBody)),
                status = "success"
            )
        )
    }

}
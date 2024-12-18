package pl.er.code.fwtheater.adapter.inbound.rest

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.er.code.fwtheater.application.port.dto.response.MovieResponse
import pl.er.code.fwtheater.application.port.dto.response.ResponseEnvelope
import pl.er.code.fwtheater.domain.model.search.MovieSearchCriteria
import pl.er.code.fwtheater.domain.service.MovieService

@RestController
@RequestMapping("/public/api/v1/movies")
class MovieController(@Autowired private val movieService: MovieService) {

    @GetMapping("/list", produces = arrayOf("application/json"))
    @Tag(name = "Public API", description = "APIs accessible without JWT")
    fun list(searchCriteria: MovieSearchCriteria): ResponseEntity<ResponseEnvelope<List<MovieResponse>, String>> {
        val response = ResponseEnvelope.fromPage<MovieResponse, String>(
            movieService.search(searchCriteria).map {
                MovieResponse(
                    id = it.id!!,
                    title = it.title!!
                )
            }
        )

        return ResponseEntity.ok(response)
    }

}
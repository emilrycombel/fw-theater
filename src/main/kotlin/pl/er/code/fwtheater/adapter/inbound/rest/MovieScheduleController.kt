package pl.er.code.fwtheater.adapter.inbound.rest

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pl.er.code.fwtheater.application.port.dto.request.AddMovieScheduleRequestBody
import pl.er.code.fwtheater.application.port.dto.request.UpdateMovieScheduleRequestBody
import pl.er.code.fwtheater.application.port.dto.response.MessageType
import pl.er.code.fwtheater.application.port.dto.response.MovieScheduleResponse
import pl.er.code.fwtheater.application.port.dto.response.ResponseEnvelope
import pl.er.code.fwtheater.domain.model.search.MovieScheduleSearchCriteria
import pl.er.code.fwtheater.domain.service.MovieScheduleService

@RestController
class MovieScheduleController(@Autowired private val movieSchedulerService: MovieScheduleService) {


    @Tag(name = "Public API", description = "APIs accessible without JWT")
    @GetMapping("/public/api/v1/movie-schedules/list", produces = arrayOf("application/json"))
    fun list(searchCriteria: MovieScheduleSearchCriteria): ResponseEntity<ResponseEnvelope<List<MovieScheduleResponse>, String>> {
        val response = ResponseEnvelope.fromPage<MovieScheduleResponse, String>(
            movieSchedulerService.search(searchCriteria) { MovieScheduleResponse.fromDomain(it) }
        )

        return ResponseEntity.ok(response)
    }

    @Tag(name = "Internal API", description = "APIs requiring JWT authentication")
    @PostMapping("/api/movie-schedules", produces = arrayOf("application/json"), consumes = arrayOf("application/json"))
    fun post(@RequestBody addMovieScheduleRequestBody: AddMovieScheduleRequestBody): ResponseEntity<ResponseEnvelope<MovieScheduleResponse, String>> {
        return ResponseEntity.ok(
            ResponseEnvelope<MovieScheduleResponse, String>(
                data = MovieScheduleResponse.fromDomain(movieSchedulerService.addSchedule(addMovieScheduleRequestBody)),
                status = "success"
            )
        )
    }

    @PatchMapping(
        "/api/movie-schedules",
        produces = arrayOf("application/json"),
        consumes = arrayOf("application/json")
    )
    @Tag(name = "Internal API", description = "APIs requiring JWT authentication")
    fun patch(@RequestBody updateMovieScheduleRequestBody: UpdateMovieScheduleRequestBody): ResponseEntity<ResponseEnvelope<MovieScheduleResponse, String>> {
        return ResponseEntity.ok(
            ResponseEnvelope<MovieScheduleResponse, String>(
                data = MovieScheduleResponse.fromDomain(
                    movieSchedulerService.updateSchedule(
                        updateMovieScheduleRequestBody
                    )
                ),
                status = "success"
            )
        )
    }

    @DeleteMapping("/api/movie-schedules/{id}", produces = arrayOf("application/json"))
    @Tag(name = "Internal API", description = "APIs requiring JWT authentication")
    fun delete(@PathVariable("id") movieScheduleId: String): ResponseEntity<ResponseEnvelope<String, String>> {
        movieSchedulerService.delete(movieScheduleId)

        return ResponseEntity.ok(
            ResponseEnvelope<String, String>(
                data = movieScheduleId,
                status = "success",
                message = "Movie schedule ${movieScheduleId} has been deleted",
                messageType = MessageType.INFO
            )
        )
    }

}
package pl.er.code.fwtheater.adapter.inbound.rest

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pl.er.code.fwtheater.application.port.dto.request.AddMovieScheduleRequestBody
import pl.er.code.fwtheater.application.port.dto.response.MovieScheduleResponse
import pl.er.code.fwtheater.application.port.dto.response.ResponseEnvelope
import pl.er.code.fwtheater.domain.service.MovieScheduleService

@RestController
@RequestMapping("/movie-schedules")
class MovieScheduleController(@Autowired private val movieSchedulerService: MovieScheduleService) {


    @GetMapping("/list", produces = arrayOf("application/json"))
    fun list(): ResponseEntity<ResponseEnvelope<List<MovieScheduleResponse>, String>> {
        return ResponseEntity.ok(
            ResponseEnvelope<List<MovieScheduleResponse>, String>(
                data = emptyList<MovieScheduleResponse>(),
                status = "success"
            )
        )
    }

    @PostMapping("", produces = arrayOf("application/json"), consumes = arrayOf("application/json"))
    fun post(@RequestBody addMovieScheduleRequestBody: AddMovieScheduleRequestBody): ResponseEntity<ResponseEnvelope<MovieScheduleResponse, String>> {
        return ResponseEntity.ok(
            ResponseEnvelope<MovieScheduleResponse, String>(
                data = MovieScheduleResponse.fromDomain(movieSchedulerService.addSchedule(addMovieScheduleRequestBody)),
                status = "success"
            )
        )
    }

}
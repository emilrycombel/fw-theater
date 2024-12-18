package pl.er.code.fwtheater.adapter.inbound.rest

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import pl.er.code.fwtheater.application.port.dto.response.ResponseEnvelope
import pl.er.code.fwtheater.domain.model.FilmBaseMovieDetails
import pl.er.code.fwtheater.domain.model.Page
import pl.er.code.fwtheater.domain.service.FilmBaseService

@RestController
class OmdbController(@Autowired private val filmBaseService: FilmBaseService) {
    @GetMapping("/omdb/{movieId}", produces = arrayOf("application/json"))
    fun movieDetails(@PathVariable movieId: String): ResponseEnvelope<List<FilmBaseMovieDetails>, String> {
        val result: FilmBaseMovieDetails? = filmBaseService.getMovieDetails(movieId)
        if (result != null) {
            return ResponseEnvelope.fromPage(Page.singleItem(result))
        }

        return ResponseEnvelope.fromPage(Page.empty())
    }

}
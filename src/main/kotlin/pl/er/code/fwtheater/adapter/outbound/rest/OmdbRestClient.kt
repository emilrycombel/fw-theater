package pl.er.code.fwtheater.adapter.outbound.rest


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import org.springframework.web.reactive.function.client.WebClient
import pl.er.code.fwtheater.adapter.outbound.rest.mapper.toDomain
import pl.er.code.fwtheater.adapter.outbound.rest.model.OmdbMovieDetailsResponse
import pl.er.code.fwtheater.application.port.outbound.FilmBaseClient
import pl.er.code.fwtheater.domain.model.FilmBaseMovieDetails
import pl.er.code.fwtheater.infrastructure.common.extensions.logger


open class OmdbRestClient(
    private val apiBase: String,
    private val apiKey: String,
    private val webClientBuilder: WebClient.Builder
) :
    FilmBaseClient {

    companion object {
        val log = logger()
    }

    private val endpoint: String = "${apiBase}/"

    @CircuitBreaker(name = "movieApiCircuitBreaker", fallbackMethod = "fallbackForMovieDetails")
    override fun movieDetailsFor(filmBaseMovieId: String): FilmBaseMovieDetails? {
        try {
            return webClientBuilder.baseUrl(endpoint).build().get().uri { builder ->
                builder.queryParam("apikey", apiKey)
                builder.queryParam("i", filmBaseMovieId).build()
            }.retrieve()
                .bodyToMono(OmdbMovieDetailsResponse::class.java)
                .block()
                ?.toDomain()
        } catch (ex: Exception) {
            throw RuntimeException("${endpoint}?apikey=XxXxX&i=${filmBaseMovieId} => ${ex.message}", ex)
        }
    }

    private fun fallbackForMovieDetails(filmBaseMovieId: String, throwable: Throwable): FilmBaseMovieDetails? {
        log.atError().setMessage { "Couldn't fetch data for [{}]. OMDB Service is unavailable {}" }
            .addArgument(filmBaseMovieId)
            .addArgument(throwable.message).log()
        return FilmBaseMovieDetails(
            name = "N/A",
            description = "Service unavailable",
            releaseDate = "N/A",
            rating = "N/A",
            imdbRating = "N/A",
            runtime = "N/A",
            length = 0
        )
    }
}
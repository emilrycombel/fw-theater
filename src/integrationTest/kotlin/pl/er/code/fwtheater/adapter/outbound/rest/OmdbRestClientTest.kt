package pl.er.code.fwtheater.adapter.outbound.rest

import io.specto.hoverfly.junit.core.Hoverfly
import io.specto.hoverfly.junit.core.HoverflyConfig
import io.specto.hoverfly.junit.core.HoverflyMode
import io.specto.hoverfly.junit.core.SimulationSource
import io.specto.hoverfly.junit.dsl.HoverflyDsl.response
import io.specto.hoverfly.junit.dsl.HoverflyDsl.service
import io.specto.hoverfly.junit5.HoverflyExtension
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import pl.er.code.fwtheater.domain.model.FilmBaseMovieDetails
import pl.er.code.fwtheater.infrastructure.config.persistence.AvailableProfiles
import pl.er.code.fwtheater.infrastructure.config.persistence.RestAdaptersConfiguration
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@SpringBootTest
@ExtendWith(SpringExtension::class)
@Import(RestAdaptersConfiguration::class)
@ActiveProfiles(AvailableProfiles.INTEG_TESTS)
class OmdbRestClientTest {

    @Autowired
    lateinit var omdbRestClient: OmdbRestClient
    private val movieId: String = "tt0232500"
    private val fakeApiKey = "askldfn32"


    @Test
    fun `Should retrieve The Fast and the Furious movie for id tt0232500`() {
        val movieDetails: FilmBaseMovieDetails? = omdbRestClient.movieDetailsFor(movieId)

        assertNotNull(movieDetails, "Should return movie")
        assertEquals("The Fast and the Furious", movieDetails.name)
    }

    @Nested
    @ExtendWith(HoverflyExtension::class)
    inner class CircuitBreaker {

        private lateinit var hoverfly: Hoverfly

        @Autowired
        @Qualifier("fakeOmdbClient")
        private lateinit var mockedOmdbRestClient: OmdbRestClient


        @Test
        fun `Should open circuit when omdb 500`() {
            hoverfly = Hoverfly(
                HoverflyConfig.localConfigs().proxyPort(8501),
                HoverflyMode.SIMULATE
            )
            hoverfly.start()
            hoverfly.simulate(
                SimulationSource.dsl(
                    service("api.fake.omdb.com")
                        .get("/movies/")
                        .queryParam("apikey", "askldfn32")
                        .queryParam("i", "tt0232500")
                        .willReturn(
                            response() // Build a custom response
                                .status(500) // Return HTTP 500 Internal Server Error
                                .header("Content-Type", "application/json")
                                .body("""{"error": "Internal Server Error"}""")
                        )
                )
            )
            
            val result = mockedOmdbRestClient.movieDetailsFor("tt0232500")
            assertEquals("N/A", result?.name)
            assertEquals("Service unavailable", result?.description)
        }

        @AfterEach
        fun tearDownHoverfly() {
            hoverfly.close()
        }
    }
}
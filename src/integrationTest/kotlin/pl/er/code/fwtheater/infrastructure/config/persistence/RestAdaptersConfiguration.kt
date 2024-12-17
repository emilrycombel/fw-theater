package pl.er.code.fwtheater.infrastructure.config.persistence

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.web.reactive.function.client.WebClient
import pl.er.code.fwtheater.adapter.outbound.rest.OmdbRestClient

@TestConfiguration
class RestAdaptersConfiguration {

    @Bean
    fun omdbRestClient(
        @Value("\${api_base_urls.omdb}") apiBase: String,
        @Value("\${api_keys.omdb}") apiKey: String,
        webClientBuilder: WebClient.Builder
    ): OmdbRestClient {
        return OmdbRestClient(apiBase, apiKey, webClientBuilder);
    }

    @Bean
    @Qualifier("fakeOmdbClient")
    fun fakeOmdbRestClient(
        webClientBuilder: WebClient.Builder
    ): OmdbRestClient {
        webClientBuilder.defaultHeader("Host", "api.fake.omdb.com")
        return OmdbRestClient("http://localhost:8501", "askldfn32", webClientBuilder);
    }

}
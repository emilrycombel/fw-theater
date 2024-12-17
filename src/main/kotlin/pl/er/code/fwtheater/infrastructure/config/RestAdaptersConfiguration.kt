package pl.er.code.fwtheater.infrastructure.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient
import pl.er.code.fwtheater.adapter.outbound.rest.OmdbRestClient

@Configuration
class RestAdaptersConfiguration {

    @Bean
    fun omdbRestClient(
        @Value("\${api_base_urls.omdb}") apiBase: String,
        @Value("\${api_keys.omdb}") apiKey: String,
        webClientBuilder: WebClient.Builder
    ): OmdbRestClient {
        return OmdbRestClient(apiBase, apiKey, webClientBuilder);
    }
}
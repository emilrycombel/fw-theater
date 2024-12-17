package pl.er.code.fwtheater.adapter.outbound.rest.model

import com.fasterxml.jackson.annotation.JsonProperty

data class OmdbRating(
    @JsonProperty("Source") val source: String,
    @JsonProperty("Value") val value: String
)

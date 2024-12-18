package pl.er.code.fwtheater.infrastructure.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.er.code.fwtheater.domain.model.usecase.CreateMovieRatingUseCase
import pl.er.code.fwtheater.domain.model.usecase.CreateMovieScheduleUseCase
import pl.er.code.fwtheater.domain.model.usecase.UpdateMovieScheduleUseCase
import pl.er.code.fwtheater.domain.service.ProfanityService

@Configuration
class UseCaseConfiguration {

    @Bean
    fun createMovieScheduleUseCase(): CreateMovieScheduleUseCase {
        return CreateMovieScheduleUseCase()
    }

    @Bean
    fun updateMovieScheduleUseCase(): UpdateMovieScheduleUseCase {
        return UpdateMovieScheduleUseCase()
    }

    @Bean
    fun createMovieRatingUseCase(@Autowired(required = false) profanityService: ProfanityService?): CreateMovieRatingUseCase {
        return CreateMovieRatingUseCase(profanityService)
    }

}
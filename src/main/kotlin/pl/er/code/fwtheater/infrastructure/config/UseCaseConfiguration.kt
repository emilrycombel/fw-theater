package pl.er.code.fwtheater.infrastructure.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.er.code.fwtheater.domain.model.usecase.CreateMovieScheduleUseCase
import pl.er.code.fwtheater.domain.model.usecase.UpdateMovieScheduleUseCase

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

}
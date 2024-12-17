package pl.er.code.fwtheater.infrastructure.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.er.code.fwtheater.domain.model.usecase.CreateMovieScheduleUseCase

@Configuration
class UseCaseConfiguration {

    @Bean
    fun createMovieScheduleUseCase(): CreateMovieScheduleUseCase {
        return CreateMovieScheduleUseCase()
    }

}
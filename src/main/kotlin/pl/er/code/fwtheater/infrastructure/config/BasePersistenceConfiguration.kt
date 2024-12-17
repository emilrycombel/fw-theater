package pl.er.code.fwtheater.infrastructure.config

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(LiquibaseConfiguration::class)
class BasePersistenceConfiguration {
}
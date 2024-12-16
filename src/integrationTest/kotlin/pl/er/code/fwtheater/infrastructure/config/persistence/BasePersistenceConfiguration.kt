package pl.er.code.fwtheater.infrastructure.config.persistence

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

@TestConfiguration
@ActiveProfiles(AvailableProfiles.WITH_TEST_CONTAINER_DB)
@Import(LiquibaseConfiguration::class)
class BasePersistenceConfiguration {
}
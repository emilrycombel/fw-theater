package pl.er.code.fwtheater.infrastructure.config.persistence

import jakarta.persistence.EntityManager
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import pl.er.code.fwtheater.adapter.outbound.persistence.entity.TheaterHEntity
import pl.er.code.fwtheater.adapter.outbound.persistence.repository.TheaterHRepositoryImpl

@TestConfiguration
@ActiveProfiles(AvailableProfiles.WITH_TEST_CONTAINER_DB)
@Import(LiquibaseConfiguration::class)
class RepositoryConfiguration {

    @Bean
    fun theaterRepository(entityManager: EntityManager): TheaterHRepositoryImpl {
        return TheaterHRepositoryImpl(TheaterHEntity::class.java, entityManager)
    }

}
package pl.er.code.fwtheater.infrastructure.persistence

import org.junit.jupiter.api.DisplayNameGeneration
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.testcontainers.containers.PostgreSQLContainer
import pl.er.code.fwtheater.infrastructure.config.persistence.AvailableProfiles
import pl.er.code.fwtheater.infrastructure.config.persistence.BasePersistenceConfiguration

@SpringBootTest
@ExtendWith(SpringExtension::class)
@DisplayNameGeneration(ReplaceUnderscores::class)
@ActiveProfiles(AvailableProfiles.WITH_TEST_CONTAINER_DB)
@Import(BasePersistenceConfiguration::class)
@ContextConfiguration
abstract class DatabaseNeededTestBase {

    @Autowired
    lateinit var postgreSQLContainer: PostgreSQLContainer<*>;

    fun isTestContainerRunning(): Boolean {
        return postgreSQLContainer.isRunning
    }
}
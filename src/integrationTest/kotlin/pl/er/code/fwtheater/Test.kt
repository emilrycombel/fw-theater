package pl.er.code.fwtheater

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import pl.er.code.fwtheater.infrastructure.config.persistence.AvailableProfiles
import pl.er.code.fwtheater.infrastructure.persistence.DatabaseNeededTestBase

@SpringBootTest
@ActiveProfiles(AvailableProfiles.WITH_TEST_CONTAINER_DB)
class Test : DatabaseNeededTestBase() {


    @Test
    fun `this is a test`() {

    }
}
package pl.er.code.fwtheater.infrastructure.config

import com.zaxxer.hikari.HikariDataSource
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.core.JdbcTemplate
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName
import javax.sql.DataSource

@TestConfiguration
class DatasourceConfiguration {

    @Bean
    fun postgresContainer(): PostgreSQLContainer<*> {
        val postgresContainer = PostgreSQLContainer(DockerImageName.parse("postgres:17"))
        postgresContainer.start()

        try {
            postgresContainer.execInContainer(
                "psql",
                "-U",
                postgresContainer.databaseName,
                "-c",
                "create role admin with role ${postgresContainer.username}"
            )

            return postgresContainer
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    @Bean
    @Primary
    fun dataSource(postgresContainer: PostgreSQLContainer<*>): DataSource {
        val dataSource = HikariDataSource()
        dataSource.driverClassName = postgresContainer.driverClassName
        dataSource.jdbcUrl = postgresContainer.jdbcUrl
        dataSource.username = postgresContainer.username
        dataSource.password = postgresContainer.password

        // HikariCP settings - adjust as necessary
        dataSource.maximumPoolSize = 10

        return dataSource
    }

    @Bean
    fun jdbcTemplate(dataSource: DataSource): JdbcTemplate {
        return JdbcTemplate(dataSource)
    }
}
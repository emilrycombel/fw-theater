package pl.er.code.fwtheater.infrastructure.config

import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.core.JdbcTemplate
import javax.sql.DataSource

@Configuration
class DatasourceConfiguration {

    @Bean
    @Primary
    fun dataSource(
        @Value("\${platform.database.name}") databaseName: String,
        @Value("\${platform.database.username}") username: String,
        @Value("\${platform.database.password}") password: String,
        @Value("\${platform.database.driver-class-name}") driverName: String,
        @Value("\${platform.database.url}") url: String
    ): DataSource {
        val dataSource = HikariDataSource()
        dataSource.driverClassName = driverName
        dataSource.jdbcUrl = "jdbc:postgresql://${url}/${databaseName}"
        dataSource.username = username
        dataSource.password = password

        // HikariCP settings - adjust as necessary
        dataSource.maximumPoolSize = 10

        return dataSource
    }

    @Bean
    fun jdbcTemplate(dataSource: DataSource): JdbcTemplate {
        return JdbcTemplate(dataSource)
    }
}

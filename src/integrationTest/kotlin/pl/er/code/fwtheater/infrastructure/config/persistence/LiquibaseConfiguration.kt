package pl.er.code.fwtheater.infrastructure.config.persistence

import liquibase.integration.spring.SpringLiquibase
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import javax.sql.DataSource

@TestConfiguration
@Import(HibernateConfiguration::class, DatasourceConfiguration::class)
class LiquibaseConfiguration {

    @Bean
    fun liquibase(datasSource: DataSource): SpringLiquibase {
        val liquibase = SpringLiquibase();
        liquibase.dataSource = datasSource;
        liquibase.changeLog = "classpath:db/changelog/test-master-changelog.xml";
        liquibase.setShouldRun(true);

        return liquibase;
    }


}
package pl.er.code.fwtheater.infrastructure.config

import liquibase.integration.spring.SpringLiquibase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import javax.sql.DataSource

@Configuration
@Import(HibernateConfiguration::class, DatasourceConfiguration::class)
class LiquibaseConfiguration {

    @Bean
    fun liquibase(datasSource: DataSource): SpringLiquibase {
        val liquibase = SpringLiquibase();
        liquibase.dataSource = datasSource;
        liquibase.changeLog = "classpath:db/changelog/master-changelog.xml";
        liquibase.setShouldRun(true);

        return liquibase;
    }


}
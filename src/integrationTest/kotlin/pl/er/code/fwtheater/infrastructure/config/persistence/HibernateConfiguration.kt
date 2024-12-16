package pl.er.code.fwtheater.infrastructure.config.persistence

import com.fasterxml.jackson.databind.ObjectMapper
import org.hibernate.cfg.AvailableSettings
import org.hibernate.jpa.HibernatePersistenceProvider
import org.hibernate.type.format.jackson.JacksonJsonFormatMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import java.util.*
import javax.sql.DataSource

@TestConfiguration
@Import(DatasourceConfiguration::class)
class HibernateConfiguration {

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Bean
    fun entityManagerFactory(dataSource: DataSource): LocalContainerEntityManagerFactoryBean {
        val em = LocalContainerEntityManagerFactoryBean()
        em.dataSource = dataSource
        em.setPackagesToScan(
            "pl.er.code.fwtheater.adapter.outbound.persistence.entity"
        )
        em.setPersistenceProviderClass(HibernatePersistenceProvider::class.java);
        val vendorAdapter = HibernateJpaVendorAdapter()
        em.jpaVendorAdapter = vendorAdapter

        val properties = Properties().apply {
            put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect")
            put("showSql", "true")
            put("hibernate.order_inserts", "true")
            put("hibernate.order_updates", "true")
            put("hibernate.jdbc.batch_size", "50")
            put("hibernate.jdbc.batch_use_rewrite", "true")
            put(AvailableSettings.JSON_FORMAT_MAPPER, JacksonJsonFormatMapper(objectMapper))
        }

        em.setJpaProperties(properties);
        return em
    }

    @Bean
    fun transactionManager(entityManagerFactory: LocalContainerEntityManagerFactoryBean): JpaTransactionManager {
        val transactionManager = JpaTransactionManager()
        transactionManager.entityManagerFactory = entityManagerFactory.`object`
        return transactionManager
    }

    @Bean
    fun jsonFormatMapperCustomizer(objectMapper: ObjectMapper): HibernatePropertiesCustomizer {
        return HibernatePropertiesCustomizer { properties ->
            properties[AvailableSettings.JSON_FORMAT_MAPPER] = JacksonJsonFormatMapper(objectMapper)
        }
    }
}

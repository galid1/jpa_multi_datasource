package com.galid.jpa_study.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.PropertySource
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

@Configuration
@PropertySource("classpath:application.yml")
@EnableJpaRepositories(
    basePackages = ["com.galid.jpa_study.repository"],
    entityManagerFactoryRef = "masterEntityManagerFactory",
    transactionManagerRef = "masterTransactionManager"
)
class MainDatasourceConfig {
    @Bean
    @Primary
    fun masterEntityManagerFactory(): LocalContainerEntityManagerFactoryBean {
        val emf = LocalContainerEntityManagerFactoryBean()
        emf.dataSource = masterDatasource()
        emf.setPackagesToScan("com.galid.jpa_study.entity.user")
        emf.jpaVendorAdapter = HibernateJpaVendorAdapter()
        emf.setJpaPropertyMap(
            mapOf(
                "hibernate.hbm2ddl.auto" to "create",
                "hibernate.dialect" to "org.hibernate.dialect.H2Dialect"
            )
        )
        return emf
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.master-datasource")
    fun masterDatasource(): DataSource {
        return DataSourceBuilder.create().build()
    }

    @Bean
    @Primary
    fun masterTransactionManager(): PlatformTransactionManager {
        val tm = JpaTransactionManager()
        tm.entityManagerFactory = masterEntityManagerFactory().`object`
        return tm
    }
}
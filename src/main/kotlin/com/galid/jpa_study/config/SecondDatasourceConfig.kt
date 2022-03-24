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
    basePackages = ["com.galid.jpa_study.second_repository"],
    entityManagerFactoryRef = "masterEntityManagerFactory",
    transactionManagerRef = "masterTransactionManager"
)
class SecondDatasourceConfig {
    @Bean
    fun secondEntityManagerFactory(): LocalContainerEntityManagerFactoryBean {
        val emf = LocalContainerEntityManagerFactoryBean()
        emf.dataSource = secondDatasource()
        emf.setPackagesToScan("com.galid.jpa_study.entity")
        emf.jpaVendorAdapter = HibernateJpaVendorAdapter()
        emf.setJpaPropertyMap(
            mapOf(
                "hibernate.hbm2ddl.auto" to "create",
                "hibernate.dialect" to "org.hibernate.dialect.MySQL8Dialect"
            )
        )
        return emf
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.second-datasource")
    fun secondDatasource(): DataSource {
        return DataSourceBuilder.create().build()
    }

    @Bean
    fun secondTransactionManager(): PlatformTransactionManager {
        val tm = JpaTransactionManager()
        tm.entityManagerFactory = secondEntityManagerFactory().`object`
        return tm
    }
}
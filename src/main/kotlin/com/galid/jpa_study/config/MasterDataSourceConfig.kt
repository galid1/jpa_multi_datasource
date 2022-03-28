package com.galid.jpa_study.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

@Configuration
@EnableJpaRepositories(
    basePackages = ["com.galid.jpa_study.dest_domain"],
    entityManagerFactoryRef = "masterEntityManagerFactory",
    transactionManagerRef = "masterTransactionManager"
)
class MasterDataSourceConfig {
    @Bean
    @Qualifier("destDataSource")
    @Primary
    fun masterDataSource(): DataSource {
        return DataSourceBuilder.create()
            .username("sa")
            .password("")
            .url("jdbc:h2:mem:testdb")
            .driverClassName("org.h2.Driver")
            .build()
    }

    @Bean
    @Qualifier("masterEntityManagerFactory")
    @Primary
    fun masterEntityManagerFactory(): LocalContainerEntityManagerFactoryBean {
        val emf = LocalContainerEntityManagerFactoryBean()
        emf.dataSource = masterDataSource()
        emf.setPackagesToScan("com.galid.jpa_study.dest_domain",)
        emf.jpaVendorAdapter = HibernateJpaVendorAdapter()
        return emf
    }

    @Bean
    @Qualifier("masterTransactionManager")
    @Primary
    fun masterTransactionManager(): PlatformTransactionManager {
        val tm = JpaTransactionManager()
        tm.entityManagerFactory = masterEntityManagerFactory().`object`
        return tm
    }
}
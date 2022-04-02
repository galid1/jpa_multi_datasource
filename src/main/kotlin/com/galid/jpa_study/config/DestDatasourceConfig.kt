package com.galid.jpa_study.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource

@Configuration
@EnableJpaRepositories(
    basePackages = ["com.galid.jpa_study.dest_domain.repo"],
    entityManagerFactoryRef = "destEntityManagerFactory",
    transactionManagerRef = "destTransactionManager"
)
class DestDataSourceConfig {
    @Bean
    @Qualifier("destDataSource")
    @Primary
    fun destDataSource(): DataSource {
        return DataSourceBuilder.create()
            .username("business_center")
            .password("GNJKApkLKNc!kcMee-BPxMY7Pk8eQ2JG")
            .url("jdbc:mysql://alpha-business1.cusieftkzlyg.ap-northeast-2.rds.amazonaws.com:3306/business_center?serverTimezone=UTC")
            .driverClassName("com.mysql.cj.jdbc.Driver")
            .build()
    }

    @Bean
    @Qualifier("destEntityManagerFactory")
    @Primary
    fun destEntityManagerFactory(): LocalContainerEntityManagerFactoryBean {
        val emf = LocalContainerEntityManagerFactoryBean()
        emf.dataSource = destDataSource()
        emf.setPackagesToScan("com.galid.jpa_study.dest_domain.entity")
        emf.jpaVendorAdapter = HibernateJpaVendorAdapter()
        emf.setJpaPropertyMap(
            mapOf(
                "hibernate.implicit_naming_strategy" to "org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy",
                "hibernate.physical_naming_strategy" to "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy",
                "hibernate.dialect" to "org.hibernate.dialect.MySQL8Dialect",
                "hibernate.hbm2ddl.auto" to "create",
            )
        )
        return emf
    }

    @Bean
    @Qualifier("destTransactionManager")
    @Primary
    fun destTransactionManager(): PlatformTransactionManager {
        val tm = JpaTransactionManager()
        tm.entityManagerFactory = destEntityManagerFactory().`object`
        tm.dataSource = destDataSource()
        return tm
    }
}
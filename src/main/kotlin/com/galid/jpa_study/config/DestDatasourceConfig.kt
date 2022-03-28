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
    entityManagerFactoryRef = "destEntityManagerFactory",
    transactionManagerRef = "destTransactionManager"
)
class DestDataSourceConfig {
    @Bean
    @Qualifier("destDataSource")
    fun destDataSource(): DataSource {
        return DataSourceBuilder.create()
            .username("root")
            .password("1234")
            .url("jdbc:mysql://localhost:3306/test?serverTimezone=UTC")
            .driverClassName("com.mysql.cj.jdbc.Driver")
            .build()
    }

    @Bean
    @Qualifier("destEntityManagerFactory")
    fun destEntityManagerFactory(): LocalContainerEntityManagerFactoryBean {
        val emf = LocalContainerEntityManagerFactoryBean()
        emf.dataSource = destDataSource()
        emf.setPackagesToScan("com.galid.jpa_study.dest_domain")
        emf.jpaVendorAdapter = HibernateJpaVendorAdapter()
//        emf.setJpaPropertyMap(
//            mapOf(
//                "hibernate.hbm2ddl.auto" to "create",
//                "hibernate.dialect" to "org.hibernate.dialect.MySQL8Dialect"
//            )
//        )
        return emf
    }

    @Bean
    @Qualifier("destTransactionManager")
    fun destTransactionManager(): PlatformTransactionManager {
        val tm = JpaTransactionManager()
        tm.entityManagerFactory = destEntityManagerFactory().`object`
        return tm
    }
}
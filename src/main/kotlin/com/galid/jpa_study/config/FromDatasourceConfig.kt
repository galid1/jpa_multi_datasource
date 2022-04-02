package com.galid.jpa_study.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource

@Configuration
@EnableJpaRepositories(
    basePackages = ["com.galid.jpa_study.from_domain"],
    entityManagerFactoryRef = "fromEntityManagerFactory",
    transactionManagerRef = "fromTransactionManager"
)
class FromDatasourceConfig {
    @Bean
    @Qualifier("fromDataSource")
    fun fromDataSource(): DataSource {
        return DataSourceBuilder.create()
            .username("kotisaari")
            .password("QNehdT@^Ehdsp92")
            .url("jdbc:mysql://alpha-business1.cusieftkzlyg.ap-northeast-2.rds.amazonaws.com:3306/kotisaari_finance")
            .driverClassName("com.mysql.cj.jdbc.Driver")
            .build()
    }

    @Bean
    @Qualifier("fromEntityManagerFactory")
    fun fromEntityManagerFactory(): LocalContainerEntityManagerFactoryBean {
        val emf = LocalContainerEntityManagerFactoryBean()
        emf.dataSource = fromDataSource()
        emf.setPackagesToScan("com.galid.jpa_study.from_domain")
        emf.jpaVendorAdapter = HibernateJpaVendorAdapter()
        emf.setJpaPropertyMap(
            mapOf(
//                "hibernate.implicit_naming_strategy" to "org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy",
                "hibernate.physical_naming_strategy" to "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy",
                "hibernate.dialect" to "org.hibernate.dialect.PostgreSQL10Dialect"
            )
        )
        return emf
    }

    @Bean
    @Qualifier("fromTransactionManager")
    fun fromTransactionManager(): PlatformTransactionManager {
        val tm = JpaTransactionManager()
        tm.entityManagerFactory = fromEntityManagerFactory().`object`
        tm.dataSource = fromDataSource()
        return tm
    }
}
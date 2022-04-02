package com.galid.jpa_study.job

import com.galid.jpa_study.config.encrypt.EncryptionConverter
import com.galid.jpa_study.dest_domain.entity.AccountType.BUSINESS_ACCOUNT
import com.galid.jpa_study.dest_domain.entity.AccountType.BUSINESS_USER
import com.galid.jpa_study.dest_domain.entity.BusinessRegistrationType
import com.galid.jpa_study.from_domain.BusinessRegistrationEntity
import com.galid.jpa_study.service.BusinessRegistrationCommandService
import com.galid.jpa_study.service.commands.CreateBusinessRegistrationCommand
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.database.JdbcCursorItemReader
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.transaction.TransactionDefinition.ISOLATION_DEFAULT
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.interceptor.DefaultTransactionAttribute
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource

@Configuration
class MigrationFromBAJobConfig(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory,
    @Qualifier("fromDataSource") private val fromDataSource: DataSource,
    private val businessRegistrationCommandService: BusinessRegistrationCommandService,
    private val converter: EncryptionConverter
) {

    @Bean
    fun migrationJob(jobRepository: JobRepository): Job {
        return jobBuilderFactory.get("migrateFromBAJob")
            .incrementer(RunIdIncrementer())
            .repository(jobRepository)
            .start(migrationStep())
            .build()
    }

    @Bean
    fun migrationStep(): Step {
        return stepBuilderFactory.get("migrateFromBAStep")
            .chunk<BusinessRegistrationEntity, Void>(CHUNK_SIZE)
            .reader(baReader())
            .processor(processor())
            .writer(writer())
            .transactionAttribute(attributesForTransaction())
            .build()
    }

    private fun attributesForTransaction(): DefaultTransactionAttribute {
        val transactionAttribute = DefaultTransactionAttribute()
        transactionAttribute.setPropagationBehavior(Propagation.NOT_SUPPORTED.value())
        transactionAttribute.setIsolationLevel(Isolation.DEFAULT.value())
        return transactionAttribute
    }

    @Bean
    @StepScope
    fun baReader(): JdbcCursorItemReader<BusinessRegistrationEntity> {
        return JdbcCursorItemReaderBuilder<BusinessRegistrationEntity>()
            .sql("""
                SELECT *
                FROM bl_business_info;
            """.trimIndent())
            .rowMapper(BeanPropertyRowMapper(BusinessRegistrationEntity::class.java))
            .dataSource(fromDataSource)
            .fetchSize(CHUNK_SIZE)
            .name("read")
            .build()
    }

    @Bean
    @StepScope
    fun processor(): ItemProcessor<BusinessRegistrationEntity, Void> {
        return ItemProcessor {
            val command = CreateBusinessRegistrationCommand(
                accountType = BUSINESS_USER,
                accountId = it.advertiserId,
                companyName = it.companyName!!,
                businessRegistrationType = null,
                businessNumber = it.businessNumber,
                registrationNumber = null,
                registrationPaperPhotoId = null,
                ownerName = it.ceoName!!,
                businessType = it.businessItem,
                businessItem = it.businessType,
                address = converter.convertToDatabaseColumn(it.address),
                contact = null,
                email = converter.convertToDatabaseColumn(it.email),
                subEmail = converter.convertToDatabaseColumn(it.subEmail)
            )

            businessRegistrationCommandService.createBusinessRegistration(command)

            null
        }
    }

    @Bean
    @StepScope
    fun writer(): ItemWriter<Void> {
        return ItemWriter<Void> {}
    }

    companion object {
        const val CHUNK_SIZE = 5
    }
}
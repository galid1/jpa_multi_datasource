package com.galid.jpa_study.job

import com.galid.jpa_study.from_domain.BusinessRegistrationEntity
import com.galid.jpa_study.service.BusinessRegistrationCommandService
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
import org.springframework.batch.item.database.JpaItemWriter
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.BeanPropertyRowMapper
import javax.sql.DataSource

@Configuration
class MigrationFromBAJobConfig(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory,
    @Qualifier("fromDataSource") private val businessPlatformDataSource: DataSource,
    @Qualifier("destDataSource") private val businessCenterDataSource: DataSource,
    private val businessRegistrationCommandService: BusinessRegistrationCommandService
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
            .build()
    }

    @Bean
    @StepScope
    fun baReader(): JdbcCursorItemReader<BusinessRegistrationEntity> {
        return JdbcCursorItemReaderBuilder<BusinessRegistrationEntity>()
            .dataSource(businessPlatformDataSource)
            .fetchSize(CHUNK_SIZE)
            .sql("""
                SELECT *
                FROM business_registrations
            """.trimIndent())
            .rowMapper(BeanPropertyRowMapper(BusinessRegistrationEntity::class.java))
            .name("baReader")
            .build()

    }

    @Bean
    @StepScope
    fun processor(): ItemProcessor<BusinessRegistrationEntity, Void> {
        return ItemProcessor {
            println(it.name)
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
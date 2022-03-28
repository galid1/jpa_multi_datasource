package com.galid.jpa_study

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableBatchProcessing
class JpaStudyApplication

fun main(args: Array<String>) {
    runApplication<JpaStudyApplication>(*args)
}

package com.galid.jpa_study.dest_domain

import com.galid.jpa_study.dest_domain.BusinessRegistrationUpdateHistoryEntity
import org.springframework.data.jpa.repository.JpaRepository

interface BusinessRegistrationUpdateHistoryRepository: JpaRepository<BusinessRegistrationUpdateHistoryEntity, Long> {
    fun findByBusinessRegistrationId(businessRegistrationId: Long): List<BusinessRegistrationUpdateHistoryEntity>
}
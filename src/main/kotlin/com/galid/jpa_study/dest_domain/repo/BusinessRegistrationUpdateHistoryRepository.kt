package com.galid.jpa_study.dest_domain.repo

import com.galid.jpa_study.dest_domain.entity.BusinessRegistrationUpdateHistoryEntity
import org.springframework.data.jpa.repository.JpaRepository

interface BusinessRegistrationUpdateHistoryRepository: JpaRepository<BusinessRegistrationUpdateHistoryEntity, Long> {
    fun findByBusinessRegistrationId(businessRegistrationId: Long): List<BusinessRegistrationUpdateHistoryEntity>
}
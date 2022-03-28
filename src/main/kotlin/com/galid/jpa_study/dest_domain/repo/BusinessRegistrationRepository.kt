package com.galid.jpa_study.dest_domain.repo

import com.galid.jpa_study.dest_domain.entity.BusinessRegistrationEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

interface BusinessRegistrationRepository: JpaRepository<BusinessRegistrationEntity, Long> {
    fun findByBusinessNumber(businessNumber: String): BusinessRegistrationEntity?
}
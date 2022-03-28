package com.galid.jpa_study.dest_domain

import com.galid.jpa_study.dest_domain.BusinessRegistrationEntity
import org.springframework.data.jpa.repository.JpaRepository

interface BusinessRegistrationRepository : JpaRepository<BusinessRegistrationEntity, Long>